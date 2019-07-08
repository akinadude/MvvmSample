package ru.akinadude.mvvmsample

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import ru.akinadude.mvvmsample.model.Note
import ru.akinadude.mvvmsample.model.NoteRepository

class NoteViewModel(
    application: Application
) : AndroidViewModel(application) {

    private var repository: NoteRepository
    private var allNotes: LiveData<List<Note>>

    //can be passed in constructor
    //or can be injected by Koin
    init {
        repository = NoteRepository(application)
        allNotes = repository.getAllNotes()
    }

    fun insert(note: Note) = repository.insert(note)

    fun update(note: Note) = repository.update(note)

    fun delete(note: Note) = repository.delete(note)

    fun deleteAllNotes() = repository.deleteAllNotes()

    fun getAllNotes(): LiveData<List<Note>> = repository.getAllNotes()

    override fun onCleared() {
        super.onCleared()
    }
}