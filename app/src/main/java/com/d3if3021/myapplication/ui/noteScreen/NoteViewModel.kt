package com.d3if3021.myapplication.ui.noteScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.d3if3021.myapplication.db.NoteEntity
import kotlinx.coroutines.launch

class NoteViewModel(private val repository: NoteRepository) : ViewModel() {
    val allNotes: LiveData<List<NoteEntity>> = repository.allNotes

    fun insert(note: NoteEntity) {
        viewModelScope.launch {
            repository.insert(note)
        }
    }
    fun delete(noteId: Long) {
        viewModelScope.launch {
            repository.delete(noteId)
        }
    }
}





