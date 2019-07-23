package ru.akinadude.mvvmsample

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_rv_note.view.*
import ru.akinadude.mvvmsample.model.Note

class NoteViewHolder(itemView: View, private val itemClickListener: OnItemClickListener) :
    RecyclerView.ViewHolder(itemView) {

    fun bind(note: Note) {
        itemView.text_view_title.text = note.title
        itemView.text_view_description.text = note.description
        itemView.text_view_priority.text = note.priority.toString()
        itemView.setOnClickListener { itemClickListener.onClick(note) }
    }
}