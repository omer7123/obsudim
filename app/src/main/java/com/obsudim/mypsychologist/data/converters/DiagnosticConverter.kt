package com.obsudim.mypsychologist.data.converters

import com.obsudim.mypsychologist.data.model.AnswersOfQuestionsModel
import com.obsudim.mypsychologist.data.model.BorderOfTestModel
import com.obsudim.mypsychologist.data.model.ConclusionOfTestModel
import com.obsudim.mypsychologist.data.model.QuestionOfTestModel
import com.obsudim.mypsychologist.data.model.ResultAfterSaveModel
import com.obsudim.mypsychologist.data.model.SaveTestResultModel
import com.obsudim.mypsychologist.data.model.ScaleResultForHistoryModel
import com.obsudim.mypsychologist.data.model.ScalesOfTestInfoModel
import com.obsudim.mypsychologist.data.model.TestInfoForPassingModel
import com.obsudim.mypsychologist.data.model.TestInfoModel
import com.obsudim.mypsychologist.data.model.TestModel
import com.obsudim.mypsychologist.data.model.TestResultsGetModel
import com.obsudim.mypsychologist.domain.entity.diagnosticEntity.AnswersOfQuestionsEntity
import com.obsudim.mypsychologist.domain.entity.diagnosticEntity.BorderOfTestEntity
import com.obsudim.mypsychologist.domain.entity.diagnosticEntity.ConclusionOfTestEntity
import com.obsudim.mypsychologist.domain.entity.diagnosticEntity.QuestionOfTestEntity
import com.obsudim.mypsychologist.domain.entity.diagnosticEntity.ResultAfterSaveEntity
import com.obsudim.mypsychologist.domain.entity.diagnosticEntity.SaveTestResultEntity
import com.obsudim.mypsychologist.domain.entity.diagnosticEntity.ScaleResultForHistoryEntity
import com.obsudim.mypsychologist.domain.entity.diagnosticEntity.ScalesOfTestInfoEntity
import com.obsudim.mypsychologist.domain.entity.diagnosticEntity.TestEntity
import com.obsudim.mypsychologist.domain.entity.diagnosticEntity.TestInfoEntity
import com.obsudim.mypsychologist.domain.entity.diagnosticEntity.TestInfoForPassingEntity
import com.obsudim.mypsychologist.domain.entity.diagnosticEntity.TestResultsGetEntity

fun TestModel.toEntity(mainLink: String) =
    TestEntity(id, title, description, shortDesc, mainLink.dropLast(1) + link)

fun SaveTestResultEntity.toModel() =
    SaveTestResultModel(testId, date, results)


fun TestResultsGetModel.toEntity() =
    TestResultsGetEntity(testId, testResultId, datetime, scaleResults.map { it.toEntity() })

fun ScaleResultForHistoryModel.toEntity()=
    ScaleResultForHistoryEntity(scaleId, score, scaleTitle, maxScore, conclusion, color, userRecommendation)

fun TestInfoModel.toEntity(): TestInfoEntity {
    return TestInfoEntity(title, description, shortDesc, id, scales.map { it.toEntity() })
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

