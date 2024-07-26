package com.example.mypsychologist.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TestModel(
    val title: String,
    val description: String,
    @SerialName("short_desc")
    val shortDesc: String,
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

@Serializable
data class TestResultsGetModel(
    @SerialName("test_id")
    val testId: String,
    @SerialName("test_result_id")
    val testResultId: String,
    val datetime: String,
    @SerialName("scale_results")
    val scaleResults: List<ScaleResultModel>
)