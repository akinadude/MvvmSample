package ru.akinadude.mvvmsample

import ru.akinadude.mvvmsample.model.Task
import ru.akinadude.mvvmsample.network.api.TodoistApi
import kotlin.coroutines.CoroutineContext

class GetTasksInteractor(coroutineContext: CoroutineContext) :
    Interactor<List<Task>>(coroutineContext) {

    private val api = TodoistApi()

    //todo unify base interactor. Bring common code into one place

    suspend fun getTasks(authToken: String): Result<List<Task>> = requestData {
        try {
            val value = api.getActiveTasks2(authToken)
            Result.Success(value)
        } catch (e: Throwable) {
            Result.Failure(e)
        }
    }
}