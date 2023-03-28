package com.d3if3021.myapplication

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.d3if3021.myapplication.databinding.ActivityMainBinding
import java.util.*

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var noteAdapter: NoteAdapter
    private var dataList = mutableListOf<Note>()

    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup RecyclerView
        noteAdapter = NoteAdapter(dataList)
        binding.rvNoteList.layoutManager = LinearLayoutManager(this)
        binding.rvNoteList.adapter = noteAdapter
        updateUI()
        noteAdapter.notifyDataSetChanged()


        // Setup set alarm button
        binding.btnSetAlarm.setOnClickListener {
            // Get user selected sleep time
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, binding.timePicker.hour)
            calendar.set(Calendar.MINUTE, binding.timePicker.minute)
            calendar.set(Calendar.SECOND, 0)

            // Create intent to trigger when alarm rings
            val intent = Intent(this, AlarmReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

            // Get instance of AlarmManager
            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

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
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

        binding.btnCreateNote.setOnClickListener {
            val intent = Intent(this, NoteActivity::class.java)
            startActivityForResult(intent, 1)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == RESULT_OK) {
            val newNote = data?.getSerializableExtra("note_data") as? Note
            newNote?.let {
                dataList.add(0, it)
                updateUI()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateUI() {
        if (dataList.isEmpty()) {
            binding.rvNoteList.visibility = View.GONE
            binding.tvEmpty.visibility = View.VISIBLE
        } else {
            binding.rvNoteList.visibility = View.VISIBLE
            binding.tvEmpty.visibility = View.GONE
            noteAdapter.notifyDataSetChanged()
        }
    }
}
