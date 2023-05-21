package com.d3if3021.myapplication.ui

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.d3if3021.myapplication.AlarmReceiver
import com.d3if3021.myapplication.ui.noteScreen.NoteAdapter
import com.d3if3021.myapplication.R
import com.d3if3021.myapplication.databinding.FragmentJamBinding
import com.d3if3021.myapplication.db.NoteDatabase
import com.d3if3021.myapplication.ui.noteScreen.NoteRepository
import com.d3if3021.myapplication.ui.noteScreen.NoteViewModel
import com.d3if3021.myapplication.ui.noteScreen.NoteViewModelFactory
import java.util.*

@Suppress("DEPRECATION")
class JamFragment : Fragment(R.layout.fragment_jam) {

    private lateinit var binding: FragmentJamBinding
    private lateinit var noteAdapter: NoteAdapter
    private lateinit var noteViewModel: NoteViewModel

    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentJamBinding.bind(view)

        // Inisialisasi ViewModel
        val noteDao = NoteDatabase.getDatabase(requireContext()).noteDao()
        val repository = NoteRepository(noteDao)
        noteViewModel = ViewModelProvider(this, NoteViewModelFactory(repository))[NoteViewModel::class.java]

        // Setup RecyclerView
        noteAdapter = NoteAdapter(noteViewModel,this)
        binding.rvNoteList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvNoteList.adapter = noteAdapter

        // Amati perubahan pada data catatan
        noteViewModel.allNotes.observe(viewLifecycleOwner) { notes ->
            notes?.let {
                noteAdapter.setData(it)
                updateUI(it.isEmpty())
            }
        }

        // Setup set alarm button
        binding.btnSetAlarm.setOnClickListener {
            // Get user selected sleep time
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, binding.timePicker.hour)
            calendar.set(Calendar.MINUTE, binding.timePicker.minute)
            calendar.set(Calendar.SECOND, 0)

            // Create intent to trigger when alarm rings
            val intent = Intent(requireContext(), AlarmReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                requireContext(),
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )

            // Get instance of AlarmManager
            val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager

            // Set alarm at the selected time
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    pendingIntent
                )
            } else {
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    pendingIntent
                )
            }

            // Show message to user that alarm has been set
            val message = "Alarm has been set at ${binding.timePicker.hour}:${binding.timePicker.minute}"
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }

        binding.btnCreateNote.setOnClickListener {
            val action = JamFragmentDirections.actionJamFragmentToNoteFragment()
            findNavController().navigate(action)
        }

        binding.btnSaran.setOnClickListener{
            val action = JamFragmentDirections.actionJamFragmentToSaranFragment()
            findNavController().navigate(action)
        }

        setHasOptionsMenu(true)
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_about) {
            val action = JamFragmentDirections.actionJamFragmentToAboutFragment()
            findNavController().navigate(action)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateUI(isEmpty: Boolean) {
        if (isEmpty) {
            binding.rvNoteList.visibility = View.GONE
            binding.tvEmpty.visibility = View.VISIBLE
        } else {
            binding.rvNoteList.visibility = View.VISIBLE
            binding.tvEmpty.visibility = View.GONE
        }
    }
}



