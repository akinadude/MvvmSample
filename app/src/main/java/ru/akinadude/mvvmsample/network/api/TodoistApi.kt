package ru.akinadude.mvvmsample.network.api

import ru.akinadude.mvvmsample.model.Task
import ru.akinadude.mvvmsample.Result

//todo what about passing it as a param?
// private val service: TodoistService
class TodoistApi {

    private val service = TodoistServiceFactory.createRetrofitService()

    suspend fun getActiveTasks(authToken: String): List<Task> = service.getActiveTasks(authToken)

    suspend fun getActiveTasks2(authToken: String): List<Task> = service.getActiveTasks(authToken)

    /*private fun <T, R> request(call: Call<T>, transform: (T) -> R, default: T): Either<Failure, R> {
            return try {
                val response = call.execute()
                when (response.isSuccessful) {
                    true -> Right(transform((response.body() ?: default)))
                    false -> Left(ServerError)
                }
            } catch (exception: Throwable) {
                Left(ServerError)
            }
        }*/
}
