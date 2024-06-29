package com.example.mypsychologist.data.model

import kotlinx.serialization.SerialName

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
