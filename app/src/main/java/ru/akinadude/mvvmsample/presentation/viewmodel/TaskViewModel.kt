package ru.akinadude.mvvmsample.presentation.viewmodel

import ru.akinadude.mvvmsample.TasksManager

class TaskViewModel: RequestDataViewModel() {

    //todo pass repository as a constructor parameter

    private val manager = TasksManager()

    fun getActiveTasks() = requestData {
        manager.getActiveTasks("Bearer b0804bb8419deddec564faeffb164b6b78be49c2")
    }
}