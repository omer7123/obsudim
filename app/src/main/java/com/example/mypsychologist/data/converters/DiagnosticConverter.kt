package com.example.mypsychologist.data.converters

import com.example.mypsychologist.data.model.AnswersOfQuestionsModel
import com.example.mypsychologist.data.model.BorderOfTestModel
import com.example.mypsychologist.data.model.ConclusionOfTestModel
import com.example.mypsychologist.data.model.QuestionOfTestModel
import com.example.mypsychologist.data.model.ResultAfterSaveModel
import com.example.mypsychologist.data.model.SaveTestResultModel
import com.example.mypsychologist.data.model.ScaleResultForHistoryModel
import com.example.mypsychologist.data.model.ScalesOfTestInfoModel
import com.example.mypsychologist.data.model.TestInfoForPassingModel
import com.example.mypsychologist.data.model.TestInfoModel
import com.example.mypsychologist.data.model.TestModel
import com.example.mypsychologist.data.model.TestResultsGetModel
import com.example.mypsychologist.domain.entity.diagnosticEntity.AnswersOfQuestionsEntity
import com.example.mypsychologist.domain.entity.diagnosticEntity.BorderOfTestEntity
import com.example.mypsychologist.domain.entity.diagnosticEntity.ConclusionOfTestEntity
import com.example.mypsychologist.domain.entity.diagnosticEntity.QuestionOfTestEntity
import com.example.mypsychologist.domain.entity.diagnosticEntity.ResultAfterSaveEntity
import com.example.mypsychologist.domain.entity.diagnosticEntity.SaveTestResultEntity
import com.example.mypsychologist.domain.entity.diagnosticEntity.ScaleResultForHistoryEntity
import com.example.mypsychologist.domain.entity.diagnosticEntity.ScalesOfTestInfoEntity
import com.example.mypsychologist.domain.entity.diagnosticEntity.TestEntity
import com.example.mypsychologist.domain.entity.diagnosticEntity.TestInfoEntity
import com.example.mypsychologist.domain.entity.diagnosticEntity.TestInfoForPassingEntity
import com.example.mypsychologist.domain.entity.diagnosticEntity.TestResultsGetEntity

fun TestModel.toEntity(mainLink: String) =
    TestEntity(title, description, shortDesc, testId, mainLink+linkToPicture)

fun SaveTestResultEntity.toModel() =
    SaveTestResultModel(testId, date, results)


fun TestResultsGetModel.toEntity() =
    TestResultsGetEntity(testId, testResultId, datetime, scaleResults.map { it.toEntity() })

fun ScaleResultForHistoryModel.toEntity()=
    ScaleResultForHistoryEntity(scaleId, score, scaleTitle, maxScore, conclusion, color, userRecommendation)

fun TestInfoModel.toEntity(): TestInfoEntity {
    return TestInfoEntity(title, description, shortDesc, testId, scales.map { it.toEntity() })
}

fun TestInfoForPassingModel.toEntity(): TestInfoForPassingEntity =
    TestInfoForPassingEntity(title, description, shortDesc, questions = questions.map { it.toEntity() })

private fun ScalesOfTestInfoModel.toEntity(): ScalesOfTestInfoEntity {
    return ScalesOfTestInfoEntity(borders.map { it.toEntity() }, max, min, scaleId, title)
}

private fun BorderOfTestModel.toEntity(): BorderOfTestEntity =
    BorderOfTestEntity(color, leftBorder, rightBorder, title)

fun QuestionOfTestModel.toEntity(): QuestionOfTestEntity {
    return QuestionOfTestEntity(number, text, answerOptions.map { it.toEntity() })
}

private fun AnswersOfQuestionsModel.toEntity(): AnswersOfQuestionsEntity {
    return AnswersOfQuestionsEntity(id, text, score)
}

fun ResultAfterSaveModel.toEntity(): ResultAfterSaveEntity=
    ResultAfterSaveEntity(testResultId, result.map { it.toEntity() })
private fun ConclusionOfTestModel.toEntity(): ConclusionOfTestEntity =
    ConclusionOfTestEntity(color, conclusion, scaleId, scaleTitle, score, userRecommendation)

