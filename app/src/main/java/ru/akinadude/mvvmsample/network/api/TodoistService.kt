package ru.akinadude.mvvmsample.network.api

import retrofit2.http.GET
import retrofit2.http.Header
import ru.akinadude.mvvmsample.model.Task

interface TodoistService {

    //todo introduce interactor, mapper and other stuff :)

    //todo Response<List<Task>> or just List<Task>?
    @GET("tasks")
    suspend fun getActiveTasks(
        @Header("Authorization") authToken: String
    ): List<Task>

    /*@GET("/search/users")
    fun searchUsersCo(@Query("q") searchText: String): Deferred<UsersContainer>

    @GET("/search/users")
    fun searchUsers(@Query("q") searchText: String): Single<UsersContainer>*/
}