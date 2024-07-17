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

@Serializable
data class SaveTestResultModel(
    val date: String,
    val results: List<ScaleResultModel>,
    @SerialName("test_id")
    val testId: String
)

@Serializable
data class ScaleResultModel(
    @SerialName("scale_id")
    val scaleId: String,
    val score: Int
)