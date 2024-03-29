package ru.akinadude.mvvmsample

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.akinadude.mvvmsample.model.Task
import ru.akinadude.mvvmsample.network.api.TodoistApi

class TasksManager {

    private val api = TodoistApi()

    //todo dispatcher should be specified within interactor/usecase
    suspend fun getActiveTasks(authToken: String): List<Task> = withContext(Dispatchers.IO) {
        api.getActiveTasks(authToken)
    }
}