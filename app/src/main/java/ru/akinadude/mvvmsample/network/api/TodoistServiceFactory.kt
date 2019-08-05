package ru.akinadude.mvvmsample.network.api

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.joda.time.LocalDate
import org.joda.time.LocalDateTime
import ru.akinadude.mvvmsample.network.retrofit.HttpClientFactory
import ru.akinadude.mvvmsample.network.retrofit.RetrofitFactory
import ru.akinadude.mvvmsample.network.retrofit.parse.DateDeserialiser
import ru.akinadude.mvvmsample.network.retrofit.parse.DateTimeDeserialiser

object TodoistServiceFactory {
    private const val BASE_URL = "https://api.todoist.com/rest/v1/"

    private val gson: Gson = GsonBuilder()
        .registerTypeAdapter(LocalDateTime::class.java, DateTimeDeserialiser())
        .registerTypeAdapter(LocalDate::class.java, DateDeserialiser())
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create()

    fun createRetrofitService(): TodoistService {
        val okHttpClient = HttpClientFactory.createOkHttpClient()
        val retrofit = RetrofitFactory(okHttpClient, gson, BASE_URL).createRetrofit()
        return retrofit.create(TodoistService::class.java)
    }
}