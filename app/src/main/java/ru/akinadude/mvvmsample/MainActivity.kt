package ru.akinadude.mvvmsample

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import ru.akinadude.mvvmsample.AddNoteActivity.Companion.DESCRIPTION_EXTRA
import ru.akinadude.mvvmsample.AddNoteActivity.Companion.PRIORITY_EXTRA
import ru.akinadude.mvvmsample.AddNoteActivity.Companion.TITLE_EXTRA
import ru.akinadude.mvvmsample.model.Note

class MainActivity : AppCompatActivity() {

    companion object {
        const val ADD_NOTE_REQUEST_CODE = 1
    }

    private lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        add_note_floating_action_button.setOnClickListener {
            val intent = Intent(MainActivity@ this, AddNoteActivity::class.java)
            startActivityForResult(intent, ADD_NOTE_REQUEST_CODE)
        }

        recycler_view.layoutManager = LinearLayoutManager(this)
        val adapter = NotesAdapter()
        recycler_view.adapter = adapter

        viewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)
        viewModel.getAllNotes().observe(this) { adapter.setNotes(it) }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_NOTE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val title = data?.getStringExtra(TITLE_EXTRA)
            val description = data?.getStringExtra(DESCRIPTION_EXTRA)
            val priority = data?.getIntExtra(PRIORITY_EXTRA, 1)

            if (title != null && description != null && priority != null) {
                val note = Note(title, description, priority)
                viewModel.insert(note)
                Toast.makeText(this, "Note is saved", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this, "Note is not saved", Toast.LENGTH_LONG).show()
        }
    }

    //viewmodel in general.
    //databinding and viewmodel, how do they live together?
    //koin, how it works in general? Examples of using it.
    //remember what is coroutines (scopes, contexts, ...). How to use it with viewmodel?
}
