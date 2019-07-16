package ru.akinadude.mvvmsample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_rv_note.view.*
import ru.akinadude.mvvmsample.model.Note

class NotesAdapter: RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private var notes: MutableList<Note> = ArrayList()

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(note: Note) {
            itemView.text_view_title.text = note.title
            itemView.text_view_description.text = note.description
            itemView.text_view_priority.text = note.priority.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: NoteViewHolder, position: Int) {
        viewHolder.bind(notes[position])
    }

    override fun getItemCount(): Int = notes.size

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    fun setNotes(notesToSet: List<Note>) {
        notes = notesToSet.toMutableList()
        notifyDataSetChanged()
    }

    fun getNoteAt(position: Int): Note = notes[position]
}