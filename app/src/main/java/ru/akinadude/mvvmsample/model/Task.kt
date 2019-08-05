package ru.akinadude.mvvmsample.model

import com.google.gson.annotations.SerializedName
import org.joda.time.LocalDateTime

data class Task(
    val id: Long,
    @SerializedName("content") val title: String,
    val projectId: Long,
    val order: Int,
    val completed: Boolean,
    val labelIds: List<String>,
    val priority: Int,
    val commentCount: Int,
    val created: LocalDateTime,
    val due: Due,
    val url: String
)