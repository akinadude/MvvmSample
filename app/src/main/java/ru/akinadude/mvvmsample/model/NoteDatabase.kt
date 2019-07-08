package ru.akinadude.mvvmsample.model

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {

    //TODO Part 5 — creating viewmodel

    //The implementation will be generated by Room.
    abstract fun noteDao(): NoteDao

    companion object {
        private var instance: NoteDatabase? = null

        fun getInstance(context: Context): NoteDatabase? {
            if (instance == null) {
                synchronized(NoteDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        NoteDatabase::class.java,
                        "notes_database"
                    ).fallbackToDestructiveMigration()
                        .addCallback(roomCallback)
                        .build()
                }
            }
            return instance
        }

        private val roomCallback: RoomDatabase.Callback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                instance?.let { PopulateDbAT(it).execute() }
            }
        }
    }

    private class PopulateDbAT(noteDatabase: NoteDatabase) : AsyncTask<Unit, Unit, Unit>() {

        private var noteDao: NoteDao

        init {
            noteDao = noteDatabase.noteDao()
        }

        override fun doInBackground(vararg notes: Unit): Unit {
            noteDao.insert(Note("Title1", "Description1", 1))
            noteDao.insert(Note("Title2", "Description2", 2))
            noteDao.insert(Note("Title3", "Description3", 3))
        }
    }
}