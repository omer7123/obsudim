package com.example.mypsychologist.data.model

import com.squareup.moshi.Json

data class TaskModel(
    val text: String,
    @Json(name = "user_id")
    val userId: String,
    @Json(name = "test_title")
    val testTitle: String,
    @Json(name = "text_id")
    val testId: String,
    val isCompleted: Boolean
)
