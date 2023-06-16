package com.d3if3021.myapplication.ui.noteScreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.d3if3021.myapplication.db.NoteEntity
import com.d3if3021.myapplication.network.TebakGambarApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
//    private fun retrieveData() {
//        viewModelScope.launch(Dispatchers.IO) {
//            try {
//                val response = TebakGambarApi.service.getTebakGambar()
//                if (response.isSuccessful) {
//                    val data = response.body()
//                    if (data != null) {
//                        Log.d("NoteViewModel", "Success: $data")
//                    } else {
//                        Log.d("NoteViewModel", "Failure: Response body is null")
//                    }
//                } else {
//                    Log.d("NoteViewModel", "Failure: ${response.message()}")
//                }
//            } catch (e: Exception) {
//                Log.d("NoteViewModel", "Failure: ${e.message}")
//            }
//        }
//    }


}





