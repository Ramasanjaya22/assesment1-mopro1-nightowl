package com.d3if3021.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.d3if3021.myapplication.databinding.ActivityNoteBinding

class NoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoteBinding
    private var mood: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.rgMood.setOnCheckedChangeListener setOnClickListener@{ _, checkedId ->
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

            val data = Note(
                mood = mood,
                activity = activity,
                food = food,
                note = note
            )

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("note_data", data)
            setResult(RESULT_OK, intent)
            finish()
        }

    }
}
