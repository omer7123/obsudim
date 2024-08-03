package com.example.mypsychologist.domain.entity.diagnosticEntity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class TestEntity(
    val title: String,
    val description: String,
    val descShort: String,
    val testId: String
)

data class SaveTestResultEntity(
    val testId: String,
    val date: String,
    val results: List<Int>,
)

data class ScaleResultEntity(
    val scaleId: String,
    val score: Int
)

data class ConclusionOfTestEntity(
    val color: String,
    val conclusion: String,
    val scaleId: String,
    val scaleTitle: String,
    val score: Int,
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
    val shortDesc: String,
    val testId: String,
    val scales: List<ScalesOfTestInfoEntity>,
)

data class BorderOfTestEntity(
    val color: String,
    val leftBorder: Int,
    val rightBorder: Int,
    val title: String
)

data class ScalesOfTestInfoEntity(
    val borders: List<BorderOfTestEntity>,
    val max: Int,
    val min: Int,
    val scaleId: String,
    val title: String,
)

@Parcelize
data class QuestionOfTestEntity(
    val number: Int,
    val text: String,
    val answerOptions: List<AnswersOfQuestionsEntity>
) : Parcelable

@Parcelize
data class AnswersOfQuestionsEntity(
    val id: String,
    val text: String,
    val score: Int
) : Parcelable