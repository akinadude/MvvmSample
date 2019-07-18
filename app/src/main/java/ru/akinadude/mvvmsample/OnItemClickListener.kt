package ru.akinadude.mvvmsample

import ru.akinadude.mvvmsample.model.Note

interface OnItemClickListener {
    fun onClick(note: Note)
}