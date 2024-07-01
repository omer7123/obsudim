package com.example.mypsychologist.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ManagerModel(
    val username: String,
    val description: String = "",
    val city: String = "",
    val company: String,
    val gender: String = "",
    @SerialName("birth_date")
    val birthDate: String = ""
)

@Serializable
data class TaskModel(
    val text: String,
    @SerialName("user_id")
    val userId: String,
    @SerialName("test_title")
    val testTitle: String,
    @SerialName("text_id")
    val testId: String,
    val isCompleted: Boolean
)
