package com.obsudim.mypsychologist.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TestModel(
    val id: String,
    val title: String,
    val description: String,
    @SerialName("short_desc")
    val shortDesc: String,
    val link: String,
)

@Serializable
data class SaveTestResultModel(
    @SerialName("test_id")
    val testId: String,
    val date: String,
    val results: List<Int>,
)

@Serializable
data class ResultAfterSaveModel(
    @SerialName("test_result_id")
    val testResultId: String,
    val result: List<ConclusionOfTestModel>
)

@Serializable
data class ConclusionOfTestModel(
    val color: String,
    val conclusion: String,
    @SerialName("scale_id")
    val scaleId: String,
    @SerialName("scale_title")
    val scaleTitle: String,
    val score: Float,
    @SerialName("user_recommendation")
    val userRecommendation: String,
)

@Serializable
data class TestResultsGetModel(
    @SerialName("test_id")
    val testId: String,
    @SerialName("test_result_id")
    val testResultId: String,
    val datetime: String,
    @SerialName("scale_results")
    val scaleResults: List<ScaleResultForHistoryModel>
)

@Serializable
data class ScaleResultForHistoryModel(
    @SerialName("scale_id")
    val scaleId: String,
    val score: Float,
    @SerialName("scale_title")
    val scaleTitle: String,
    @SerialName("max_score")
    val maxScore: Int,
    val conclusion: String,
    val color: String,
    @SerialName("user_recommendation")
    val userRecommendation: String,
)

@Serializable
data class BorderOfTestModel(
    val color: String,
    @SerialName("left_border")
    val leftBorder: Double,
    @SerialName("right_border")
    val rightBorder: Double,
    val title: String
)

@Serializable
data class ScalesOfTestInfoModel(
    val borders: List<BorderOfTestModel>,
    val max: Int,
    val min: Int,
    @SerialName("id")
    val scaleId: String,
    val title: String,
)
@Serializable
data class TestInfoModel(
    val id: String,
    val title: String,
    val description: String,
    @SerialName("short_desc")
    val shortDesc: String,
    @SerialName("scale")
    val scales: List<ScalesOfTestInfoModel>,
)
@Serializable
data class TestInfoForPassingModel(
    val title: String,
    val description: String,
    @SerialName("short_desc")
    val shortDesc: String,
    val questions: List<QuestionOfTestModel>
)
@Serializable
data class QuestionOfTestModel(
    val number: Int,
    val text: String,
    @SerialName("answer_choices")
    val answerOptions: List<AnswersOfQuestionsModel>
)

@Serializable
data class AnswersOfQuestionsModel(
    val id: String,
    val text: String,
    val score: Int
)
