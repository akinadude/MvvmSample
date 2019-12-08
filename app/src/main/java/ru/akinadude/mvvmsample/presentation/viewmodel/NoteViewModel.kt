package ru.akinadude.mvvmsample.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.akinadude.mvvmsample.TasksManager
import ru.akinadude.mvvmsample.model.Note
import ru.akinadude.mvvmsample.model.NoteRepository

class NoteViewModel(
    application: Application
) : AndroidViewModel(application) {

    //todo pass repository as constructor parameter

    private val _allNotes = MutableLiveData<List<Note>>()

    val allNotes: MutableLiveData<List<Note>>
        get() = _allNotes

    private var repository: NoteRepository

    //can be passed in constructor
    //or can be injected by Koin
    init {
        repository = NoteRepository(application)
    }

    fun insert(note: Note) = repository.insert(note)

    fun update(note: Note) = repository.update(note)

    fun delete(note: Note) = repository.delete(note)

    fun deleteAllNotes() = repository.deleteAllNotes()

    fun getAllNotes(): LiveData<List<Note>> = repository.getAllNotes()

    fun getAllNotes2() {
        val notes = repository.getAllNotes2()
        _allNotes.postValue(notes)
    }

    override fun onCleared() {
        super.onCleared()
    }
}