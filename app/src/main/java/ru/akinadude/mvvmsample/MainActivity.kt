package ru.akinadude.mvvmsample

import android.accounts.AccountManager
import android.accounts.AccountManagerCallback
import android.accounts.AccountManagerFuture
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import ru.akinadude.mvvmsample.AddEditNoteActivity.Companion.EXTRA_DESCRIPTION
import ru.akinadude.mvvmsample.AddEditNoteActivity.Companion.EXTRA_ID
import ru.akinadude.mvvmsample.AddEditNoteActivity.Companion.EXTRA_PRIORITY
import ru.akinadude.mvvmsample.AddEditNoteActivity.Companion.EXTRA_TITLE
import ru.akinadude.mvvmsample.model.Note
import ru.akinadude.mvvmsample.presentation.viewmodel.NoteViewModel
import ru.akinadude.mvvmsample.presentation.viewmodel.TaskViewModel

//todo target: clean architecture (coroutines, koin, unit tests, integration tests, ui tests),
// multi module project
// decent navigation approach

//todo introduce view model from aac
//todo drop all async tasks use coroutines
//todo use single activity application
//todo introduce interactors, koin
//todo introduce proper repository pattern (look at some examples, need to properly use interfaces)

class MainActivity : AppCompatActivity() {

    //todo authorise on todoist, get access token.

    //todo How does refreshing of an access token should work?

    //todo For working with permissions try easy-permissions lib
    companion object {
        const val ADD_NOTE_REQUEST_CODE = 1
        const val EDIT_NOTE_REQUEST_CODE = 2
    }

    private lateinit var viewModel: NoteViewModel
    private lateinit var taskViewModel: TaskViewModel
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
        taskViewModel = ViewModelProviders.of(this).get(TaskViewModel::class.java)
        viewModel.getAllNotes().observe(this) { adapter.submitList(it) }
        taskViewModel.getActiveTasks()

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
        ItemTouchHelper(itemTouchCallbackImpl).apply {
            attachToRecyclerView(recycler_view)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

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

    private class OnTokenAcquired : AccountManagerCallback<Bundle> {

        override fun run(result: AccountManagerFuture<Bundle>) {
            // Get the result of the operation from the AccountManagerFuture.
            val bundle: Bundle = result.result

            Log.d("Auth", "bundle: $bundle")

            // The token is a named value in the bundle. The name of the value
            // is stored in the constant AccountManager.KEY_AUTHTOKEN.
            val token: String? = bundle.getString(AccountManager.KEY_AUTHTOKEN)
            Log.d("Auth", "token: $token")
        }
    }

    private class OnError : Handler.Callback {
        override fun handleMessage(p0: Message?): Boolean {
            Log.d("Auth", "error is occurred, message: $p0")
            return true
        }
    }

    /*
    Done! 0. Authenticate in todoist API.
    API token: b0804bb8419deddec564faeffb164b6b78be49c2
    Прикрутить корутины и вытаскивание всех активных тасков

    Doing... 1. Watch video by R. Martin about clean arch

    Done! 2. Watch video https://www.youtube.com/watch?v=W2c_HWthB0Y&t=623s

    Done! 3. Прочесть статью https://developer.android.com/kotlin/coroutines

    Done! 4. Прочесть статью https://proandroiddev.com/retrofit-met-coroutines-7bbe7e86825a

    Done! 5. Прочесть статью https://proandroiddev.com/suspend-what-youre-doing-retrofit-has-now-coroutines-support-c65bd09ba067

    6. Learn four links at the bottom of the article https://developer.android.com/kotlin/coroutines

    7. Нагуглить и прочесть статьи 2 про clean arch, которые нашел в пт на работе.
    Оттуда вытянуть схему разбиения по слоям.

    8. Посмотреть еще одно видео из плэйлиста от wiseAss

    9. Add ExceptionHandler for coroutines.

    Optional
    1. Done! Watch video https://www.youtube.com/watch?v=2rO4r-JOQtA&t=2s
    */

    //todo what lib is responsible for automatic response parsing? Retrofit itself of some gson adapter lib?

    //todo sealed class Result with Value, Error flavours. Like Option in Scala :)

    //todo what if due to rotating a device network call completed and sends data to live data before viewmodel attached to view?
    // how can we deliver data to view in this case?

    //todo coroutines and viewmodel. Working with scope.

    //todo dissect logic in fragment on view and viewmodel.

    //todo add network layer -> add repository -> add interactor.

    //viewmodel in general.
    //databinding and viewmodel, how do they live together?
    //koin, how it works in general? Examples of using it.
    //remember what is coroutines (scopes, contexts, ...). How to use it with viewmodel?
}
