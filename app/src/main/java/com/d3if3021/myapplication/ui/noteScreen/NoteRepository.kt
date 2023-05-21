package com.d3if3021.myapplication.ui.noteScreen

import androidx.lifecycle.LiveData
import com.d3if3021.myapplication.db.NoteDao
import com.d3if3021.myapplication.db.NoteEntity

class NoteRepository(private val noteDao: NoteDao) {
    val allNotes: LiveData<List<NoteEntity>> = noteDao.getAllNotes()

    suspend fun insert(note: NoteEntity) {
        noteDao.insert(note)
    }
    suspend fun delete(noteId: Long) {
        noteDao.deleteNoteById(noteId)
    }

}



