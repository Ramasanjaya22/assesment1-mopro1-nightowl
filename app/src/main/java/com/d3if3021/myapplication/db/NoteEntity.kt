package com.d3if3021.myapplication.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val mood: String,
    val activity: String,
    val food: String,
    val note: String
)



