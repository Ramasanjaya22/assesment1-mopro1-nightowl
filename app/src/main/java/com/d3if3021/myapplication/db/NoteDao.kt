package com.d3if3021.myapplication.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {

    @Insert
    suspend fun insert(note: NoteEntity)

    @Query("SELECT * FROM notes")
    fun getAllNotes(): LiveData<List<NoteEntity>>

    @Query("DELETE FROM notes WHERE id = :noteId")
    suspend fun deleteNoteById(noteId: Long)

}



