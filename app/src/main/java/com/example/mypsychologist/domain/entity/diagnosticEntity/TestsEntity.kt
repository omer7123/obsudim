package com.example.mypsychologist.domain.entity.diagnosticEntity

import com.example.mypsychologist.data.model.ScalesOfTestInfoModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class TestEntity(
    val title: String,
    val description: String,
    val descShort: String,
    val testId: String
)

data class SaveTestResultEntity(
    val date: String,
    val results: List<ScaleResultEntity>,
    val testId: String
)

data class ScaleResultEntity(
    val scaleId: String,
    val score: Int
)

data class ScaleResultWithTitileEntity(
    val name: String,
    val scaleId: String,
    val score: Int
)

data class TestResultsScalesWithTitleEntity(
    val testId: String,
    val testResultId: String,
    val datetime: String,
    val scaleResults: List<ScaleResultWithTitileEntity>
)

data class TestResultsGetEntity(
    val testId: String,
    val testResultId: String,
    val datetime: String,
    val scaleResults: List<ScaleResultEntity>
)

data class TestInfoEntity(
    val title: String,
    val description: String,
    @SerialName("short_desc")
    val shortDesc: String,
    @SerialName("test_id")
    val testId: String,
    val scales: List<ScalesOfTestInfoEntity>,
)

data class BorderOfTestEntity(
    val color: String,
    @SerialName("left_border")
    val leftBorder: Int,
    @SerialName("right_border")
    val rightBorder: Int,
    val title: String
)

data class ScalesOfTestInfoEntity(
    val borders: List<BorderOfTestEntity>,
    val max: Int,
    val min: Int,
    @SerialName("scale_id")
    val scaleId: String,
    val title: String,
)

data class QuestionOfTestEntity(
    val number: Int,
    val text: String,
    val answerOptions: List<AnswersOfQuestionsEntity>
)

data class AnswersOfQuestionsEntity(
    val id: Int,
    val text: String,
    val score: Int
)