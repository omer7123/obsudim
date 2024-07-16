package com.example.mypsychologist.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TestModel(
    val title: String,
    val description: String,
    @SerialName("test_id")
    val testId: String
)