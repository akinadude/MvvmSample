package ru.akinadude.mvvmsample.model

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData

class NoteRepository(application: Application) {

    private var noteDao: NoteDao?
    private var allNotes: LiveData<List<Note>>

    init {
        val database = NoteDatabase.getInstance(application)
        noteDao = database?.noteDao()
        allNotes = noteDao!!.getAllNotes()
    }

    // todo replace async tasks by coroutines.

    fun insert(note: Note) {
        noteDao?.let { InsertNoteAT(it).execute(note) }
    }

    fun update(note: Note) {
        noteDao?.let { UpdateNoteAT(it).execute(note) }
    }

    fun delete(note: Note) {
        noteDao?.let { DeleteNoteAT(it).execute(note) }
    }

    fun deleteAllNotes() {
        noteDao?.let { DeleteAllNotesAT(it).execute() }
    }

    // fetches data in a background thread
    fun getAllNotes(): LiveData<List<Note>> = allNotes


    private class InsertNoteAT(private val noteDao: NoteDao) : AsyncTask<Note, Unit, Unit>() {

        override fun doInBackground(vararg notes: Note): Unit {
            noteDao.insert(notes[0])
        }
    }

    private class UpdateNoteAT(private val noteDao: NoteDao) : AsyncTask<Note, Unit, Unit>() {

        override fun doInBackground(vararg notes: Note): Unit {
            noteDao.update(notes[0])
        }
    }

    private class DeleteNoteAT(private val noteDao: NoteDao) : AsyncTask<Note, Unit, Unit>() {

        override fun doInBackground(vararg notes: Note): Unit {
            noteDao.delete(notes[0])
        }
    }

    private class DeleteAllNotesAT(private val noteDao: NoteDao) : AsyncTask<Unit, Unit, Unit>() {

        override fun doInBackground(vararg notes: Unit): Unit {
            noteDao.deleteAllNotes()
        }
    }
}