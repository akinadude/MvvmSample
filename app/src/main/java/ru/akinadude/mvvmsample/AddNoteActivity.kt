package ru.akinadude.mvvmsample

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_note.*

class AddNoteActivity : AppCompatActivity() {

    companion object {
        const val TITLE_EXTRA = "title_extra"
        const val DESCRIPTION_EXTRA = "description_extra"
        const val PRIORITY_EXTRA = "priority_extra"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        title_edit_text
        description_edit_text
        priority_number_picker.minValue = 1
        priority_number_picker.maxValue = 10

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
        title = "Add Note"
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
            putExtra(TITLE_EXTRA, title)
            putExtra(DESCRIPTION_EXTRA, description)
            putExtra(PRIORITY_EXTRA, priority)
        }
        setResult(RESULT_OK, intent)
        finish()

    }
}