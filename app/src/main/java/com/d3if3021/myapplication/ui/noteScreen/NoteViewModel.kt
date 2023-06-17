package com.d3if3021.myapplication.ui.noteScreen

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.d3if3021.myapplication.db.NoteEntity
import com.d3if3021.myapplication.network.UpdateWorker
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class NoteViewModel(private val repository: NoteRepository) : ViewModel() {
    val allNotes: LiveData<List<NoteEntity>> = repository.allNotes
//    init {
//        retrieveData()
//    }
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
    fun scheduleUpdater(app: Application) {
        val request = OneTimeWorkRequestBuilder<UpdateWorker>()
            .setInitialDelay(1, TimeUnit.MINUTES)
            .build()
        WorkManager.getInstance(app).enqueueUniqueWork(
            UpdateWorker.WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            request
        )
    }



}





