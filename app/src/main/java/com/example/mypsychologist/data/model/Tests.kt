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
    @SerialName("test_id")
    val testId: String,
    val date: String,
    val results: List<Int>,
)

@Serializable
data class ScaleResultModel(
    @SerialName("scale_id")
    val scaleId: String,
    val score: Int
)

@Serializable
data class ConclusionOfTestModel(
    val color: String,
    val conclusion: String,
    @SerialName("scale_id")
    val scaleId: String,
    @SerialName("scale_title")
    val scaleTitle: String,
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

@Serializable
data class BorderOfTestModel(
    val color: String,
    @SerialName("left_border")
    val leftBorder: Int,
    @SerialName("right_border")
    val rightBorder: Int,
    val title: String
)

@Serializable
data class ScalesOfTestInfoModel(
    val borders: List<BorderOfTestModel>,
    val max: Int,
    val min: Int,
    @SerialName("scale_id")
    val scaleId: String,
    val title: String,
)

@Serializable
data class TestInfoModel(
    val title: String,
    val description: String,
    @SerialName("short_desc")
    val shortDesc: String,
    @SerialName("test_id")
    val testId: String,
    val scales: List<ScalesOfTestInfoModel>,
)

@Serializable
data class QuestionOfTestModel(
    val number: Int,
    val text: String,
    @SerialName("answer_options")
    val answerOptions: List<AnswersOfQuestionsModel>
)

@Serializable
data class AnswersOfQuestionsModel(
    val id: String,
    val text: String,
    val score: Int
)
