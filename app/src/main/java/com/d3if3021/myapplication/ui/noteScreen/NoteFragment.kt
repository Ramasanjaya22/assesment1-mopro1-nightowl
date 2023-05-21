package com.d3if3021.myapplication.ui.noteScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.d3if3021.myapplication.R
import com.d3if3021.myapplication.databinding.FragmentNoteBinding
import com.d3if3021.myapplication.db.NoteDatabase
import com.d3if3021.myapplication.db.NoteEntity

class NoteFragment : Fragment() {
    private lateinit var binding: FragmentNoteBinding
    private var mood: String = ""
    private lateinit var noteViewModel: NoteViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val noteDao = NoteDatabase.getDatabase(requireContext()).noteDao()
        val repository = NoteRepository(noteDao)
        noteViewModel = ViewModelProvider(this, NoteViewModelFactory(repository))[NoteViewModel::class.java]

        binding.rgMood.check(R.id.rb_happy)
        binding.rgMood.setOnCheckedChangeListener { _, checkedId ->
            mood = when (checkedId) {
                R.id.rb_happy -> "😊"
                R.id.rb_sad -> "😢"
                R.id.rb_angry -> "😠"
                R.id.rb_flat -> "😐"
                else -> ""
            }
        }
        binding.btnSave.setOnClickListener {
            val activity = binding.etActivity.text.toString().trim()
            val food = binding.etFood.text.toString().trim()
            val note = binding.etNote.text.toString().trim()

            // validasi inputan
            if (activity.isEmpty()) {
                binding.etActivity.error = "Activity must not be empty"
                return@setOnClickListener
            }

            if (food.isEmpty()) {
                binding.etFood.error = "Food must not be empty"
                return@setOnClickListener
            }

            if (note.isEmpty()) {
                binding.etNote.error = "Note must not be empty"
                return@setOnClickListener
            }

            val newNote = NoteEntity(
                mood = mood,
                activity = activity,
                food = food,
                note = note
            )

            noteViewModel.insert(newNote)
            findNavController().navigateUp()

        }
    }

}



