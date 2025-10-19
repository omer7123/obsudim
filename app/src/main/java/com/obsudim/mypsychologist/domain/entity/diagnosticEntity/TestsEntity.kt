package com.obsudim.mypsychologist.domain.entity.diagnosticEntity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class TestEntity(
    val testId: String,
    val title: String,
    val description: String,
    val descShort: String,
    val linkToPicture: String
)

data class SaveTestResultEntity(
    val testId: String,
    val date: String,
    val results: List<Int>,
)

data class ScaleResultForHistoryEntity(
    val scaleId: String,
    val score: Float,
    val scaleTitle: String,
    val maxScore: Int,
    val conclusion: String,
    val color: String,
    val userRecommendation: String
)

data class ResultAfterSaveEntity(
    val testResultId: String,
    val result: List<ConclusionOfTestEntity>
)

data class ConclusionOfTestEntity(
    val color: String,
    val conclusion: String,
    val scaleId: String,
    val scaleTitle: String,
    val score: Float,
    val userRecommendation: String,
)

data class TestResultsGetEntity(
    val testId: String,
    val testResultId: String,
    val datetime: String,
    val scaleResults: List<ScaleResultForHistoryEntity>
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
    val leftBorder: Double,
    val rightBorder: Double,
    val title: String
)

data class ScalesOfTestInfoEntity(
    val borders: List<BorderOfTestEntity>,
    val max: Int,
    val min: Int,
    val scaleId: String,
    val title: String,
)


data class TestInfoForPassingEntity(
    val title: String,
    val description: String,
    val shortDesc: String,
    val questions: List<QuestionOfTestEntity>
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
