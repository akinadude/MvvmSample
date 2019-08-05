package ru.akinadude.mvvmsample.network.api

import ru.akinadude.mvvmsample.model.Task

//todo what about passing it as a param?
// private val service: TodoistService
class TodoistApi {

    private val service = TodoistServiceFactory.createRetrofitService()

    suspend fun getActiveTasks(authToken: String): List<Task> = service.getActiveTasks(authToken)
}
