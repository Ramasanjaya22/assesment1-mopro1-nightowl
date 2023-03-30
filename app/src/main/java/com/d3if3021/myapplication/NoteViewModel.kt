package com.d3if3021.myapplication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NoteViewModel : ViewModel() {
    val dataList = MutableLiveData<MutableList<Note>>(mutableListOf())
    fun addNoteToList(note: Note) {
        dataList.value?.apply { add(note) } ?: dataList.postValue(mutableListOf(note))
    }
}
