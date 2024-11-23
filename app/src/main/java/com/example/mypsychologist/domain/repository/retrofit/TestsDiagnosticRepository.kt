package com.example.mypsychologist.domain.repository.retrofit

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.diagnosticEntity.ResultAfterSaveEntity
import com.example.mypsychologist.domain.entity.diagnosticEntity.SaveTestResultEntity
import com.example.mypsychologist.domain.entity.diagnosticEntity.TestEntity
import com.example.mypsychologist.domain.entity.diagnosticEntity.TestInfoEntity
import com.example.mypsychologist.domain.entity.diagnosticEntity.TestInfoForPassingEntity
import com.example.mypsychologist.domain.entity.diagnosticEntity.TestResultsGetEntity

interface TestsDiagnosticRepository {
    suspend fun getAllTests(): Resource<List<TestEntity>>
    suspend fun saveTestResult(saveTestResultModel: SaveTestResultEntity): Resource<ResultAfterSaveEntity>
    suspend fun getTestResults(testId: String): Resource<List<TestResultsGetEntity>>
    suspend fun getInfoAboutTest(testId: String): Resource<TestInfoEntity>
    suspend fun getQuestionsOfTest(testId: String): Resource<TestInfoForPassingEntity>
    suspend fun getTestResult(testResultId: String): Resource<TestResultsGetEntity>
}