package com.nandroid.noteapp.ui.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nandroid.noteapp.MyApplication
import com.nandroid.noteapp.data.database.Provider
import com.nandroid.noteapp.data.models.Notes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModel : ViewModel() {
    private val _notes = MutableLiveData<List<Notes>>()
    val notes: LiveData<List<Notes>> = _notes

    fun fetchNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            val notes = Provider.provideNotesDao(MyApplication.database).getAllNotes()
            _notes.postValue(notes)
        }
    }

    fun deleteNote(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            Provider.provideNotesDao(MyApplication.database).deleteNote(id)
            fetchNotes()
        }
    }

    fun insertNote(note: Notes) {
        viewModelScope.launch(Dispatchers.IO) {
            Provider.provideNotesDao(MyApplication.database).insertNote(note)
            fetchNotes()
        }
    }


    fun updateNote(note: Notes) {
        println(note)
        viewModelScope.launch(Dispatchers.IO) {
            Provider.provideNotesDao(MyApplication.database)
                .updateNote(note)
            fetchNotes()
        }
    }
}