package ru.akinadude.mvvmsample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler_view.layoutManager = LinearLayoutManager(this)
        val adapter = NotesAdapter()
        recycler_view.adapter = adapter

        viewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)
        viewModel.getAllNotes().observe(this) { adapter.setNotes(it) }

    }

    //viewmodel in general.
    //databinding and viewmodel, how do they live together?
    //koin, how it works in general? Examples of using it.
    //remember what is coroutines (scopes, contexts, ...). How to use it with viewmodel?
}
