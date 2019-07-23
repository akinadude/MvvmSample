package ru.akinadude.mvvmsample

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import kotlinx.android.synthetic.main.activity_main.*
import ru.akinadude.mvvmsample.AddEditNoteActivity.Companion.EXTRA_DESCRIPTION
import ru.akinadude.mvvmsample.AddEditNoteActivity.Companion.EXTRA_ID
import ru.akinadude.mvvmsample.AddEditNoteActivity.Companion.EXTRA_PRIORITY
import ru.akinadude.mvvmsample.AddEditNoteActivity.Companion.EXTRA_TITLE
import ru.akinadude.mvvmsample.model.Note
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.api.Scope


class MainActivity : AppCompatActivity() {

    //todo authorise on google tasks, get access token.
    //todo create three tasks in list on google tasks.
    //todo get that three tasks by web request with access token and show them in RV.

    //todo How does refreshing of an access token work?

    companion object {
        const val ADD_NOTE_REQUEST_CODE = 1
        const val EDIT_NOTE_REQUEST_CODE = 2
    }

    private lateinit var viewModel: NoteViewModel
    private val onItemClickListener = object : OnItemClickListener {
        override fun onClick(note: Note) {
            val intent = Intent(this@MainActivity, AddEditNoteActivity::class.java).apply {
                putExtra(EXTRA_ID, note.id)
                putExtra(EXTRA_TITLE, note.title)
                putExtra(EXTRA_DESCRIPTION, note.description)
                putExtra(EXTRA_PRIORITY, note.priority)
            }
            startActivityForResult(intent, EDIT_NOTE_REQUEST_CODE)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        add_note_floating_action_button.setOnClickListener {
            val intent = Intent(this@MainActivity, AddEditNoteActivity::class.java)
            startActivityForResult(intent, ADD_NOTE_REQUEST_CODE)
        }

        recycler_view.layoutManager = LinearLayoutManager(this)
        val adapter = NotesAdapter(onItemClickListener)
        recycler_view.adapter = adapter

        viewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)
        viewModel.getAllNotes().observe(this) { adapter.submitList(it) }

        val itemTouchCallbackImpl =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean = false

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    viewModel.delete(adapter.getNoteAt(viewHolder.adapterPosition))
                    Toast.makeText(this@MainActivity, "Note deleted", Toast.LENGTH_SHORT).show()
                }
            }
        val itemTouchHelper = ItemTouchHelper(itemTouchCallbackImpl)
        itemTouchHelper.attachToRecyclerView(recycler_view)

        val SCOPE_CONTACTS_READ = Scope("https://www.googleapis.com/auth/tasks")
        val SCOPE_EMAIL = Scope(Scopes.EMAIL)
        if (!GoogleSignIn.hasPermissions(
            GoogleSignIn.getLastSignedInAccount(this@MainActivity),
            SCOPE_CONTACTS_READ,
            SCOPE_EMAIL
        )) {
            GoogleSignIn.requestPermissions(
                this@MainActivity,
                101,
                GoogleSignIn.getLastSignedInAccount(this@MainActivity),
                SCOPE_CONTACTS_READ,
                SCOPE_EMAIL);
        }
        //https://developer.android.com/training/id-auth/authenticate
        //https://developers.google.com/tasks/oauth-and-tasks-on-android
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (101 == requestCode) {
                getContacts();
            }
        }

        if (requestCode == ADD_NOTE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val title = data?.getStringExtra(EXTRA_TITLE)
            val description = data?.getStringExtra(EXTRA_DESCRIPTION)
            val priority = data?.getIntExtra(EXTRA_PRIORITY, 1)

            if (title != null && description != null && priority != null) {
                val note = Note(title, description, priority)
                viewModel.insert(note)
                Toast.makeText(this, "Note is saved", Toast.LENGTH_LONG).show()
            }
        } else if (requestCode == EDIT_NOTE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val id = data?.getIntExtra(EXTRA_ID, -1) ?: -1
            if (id == -1) {
                Toast.makeText(this, "Note can't be updated", Toast.LENGTH_LONG).show()
                return
            }

            val title = data?.getStringExtra(EXTRA_TITLE)
            val description = data?.getStringExtra(EXTRA_DESCRIPTION)
            val priority = data?.getIntExtra(EXTRA_PRIORITY, 1)
            if (title != null && description != null && priority != null) {
                val note = Note(title, description, priority)
                note.id = id
                viewModel.update(note)
                Toast.makeText(this, "Note is updated", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this, "Note is not saved", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.delete_all_notes -> {
                viewModel.deleteAllNotes()
                Toast.makeText(this, "All notes deleted", Toast.LENGTH_LONG).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    //todo scope for tasks: https://www.googleapis.com/auth/tasks

    //todo coroutines and viewmodel. Working with scope.

    //todo dissect logic in fragment on view and viewmodel.
    //todo add network layer -> add repository -> add interactor.

    //viewmodel in general.
    //databinding and viewmodel, how do they live together?
    //koin, how it works in general? Examples of using it.
    //remember what is coroutines (scopes, contexts, ...). How to use it with viewmodel?
}
