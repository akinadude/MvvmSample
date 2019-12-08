package ru.akinadude.mvvmsample.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import ru.akinadude.mvvmsample.GetTasksInteractor
import ru.akinadude.mvvmsample.Result
import ru.akinadude.mvvmsample.TasksManager
import ru.akinadude.mvvmsample.model.Task

class TaskViewModel: RequestDataViewModel() {

    //todo pass repository as a constructor parameter

    private val _tasks = MutableLiveData<Result<List<Task>>>()

    val tasks: MutableLiveData<Result<List<Task>>>
        get() = _tasks

    private val manager = TasksManager()
    private val interactor = GetTasksInteractor(Dispatchers.IO)

    fun getActiveTasks() = requestData {
        manager.getActiveTasks("Bearer b0804bb8419deddec564faeffb164b6b78be49c2")
    }

    fun getActiveTasks2() = requestData {
        val tasks = interactor.getTasks("Bearer b0804bb8419deddec564faeffb164b6b78be49c2")
        _tasks.setValue(tasks)
    }
}