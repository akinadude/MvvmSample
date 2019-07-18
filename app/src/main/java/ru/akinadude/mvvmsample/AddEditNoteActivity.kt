package ru.akinadude.mvvmsample

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_note.*

class AddEditNoteActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_TITLE = "title_extra"
        const val EXTRA_DESCRIPTION = "description_extra"
        const val EXTRA_PRIORITY = "priority_extra"
        const val EXTRA_ID = "id_extra"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        priority_number_picker.minValue = 1
        priority_number_picker.maxValue = 10

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)

        if (intent.hasExtra(EXTRA_ID)) {
            title_edit_text.setText(intent.getStringExtra(EXTRA_TITLE))
            description_edit_text.setText(intent.getStringExtra(EXTRA_DESCRIPTION))
            priority_number_picker.value = intent.getIntExtra(EXTRA_PRIORITY, 1)
            title = "Edit Note"

        } else {
            title = "Add Note"
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add_note, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.save_note -> {
                saveNote()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveNote() {
        val title = title_edit_text.text.toString()
        val description = description_edit_text.text.toString()
        val priority = priority_number_picker.value

        if (title.isBlank() || description.isBlank()) {
            Toast.makeText(this, "Please insert a title and description", Toast.LENGTH_LONG).show()
            return
        }

        val intent = Intent().apply {
            val id = intent.getIntExtra(EXTRA_ID, -1)
            putExtra(EXTRA_ID, id)
            putExtra(EXTRA_TITLE, title)
            putExtra(EXTRA_DESCRIPTION, description)
            putExtra(EXTRA_PRIORITY, priority)
        }
        setResult(RESULT_OK, intent)
        finish()

    }
}