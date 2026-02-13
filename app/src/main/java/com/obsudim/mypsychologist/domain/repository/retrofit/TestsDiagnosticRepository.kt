package com.obsudim.mypsychologist.domain.repository.retrofit

import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.domain.entity.diagnosticEntity.QuestionOfTestEntity
import com.obsudim.mypsychologist.domain.entity.diagnosticEntity.ResultAfterSaveEntity
import com.obsudim.mypsychologist.domain.entity.diagnosticEntity.SaveTestResultEntity
import com.obsudim.mypsychologist.domain.entity.diagnosticEntity.TestEntity
import com.obsudim.mypsychologist.domain.entity.diagnosticEntity.TestInfoEntity
import com.obsudim.mypsychologist.domain.entity.diagnosticEntity.TestResultsGetEntity
import kotlinx.coroutines.flow.Flow

interface TestsDiagnosticRepository {
    suspend fun getAllTests(): Resource<List<TestEntity>>
    suspend fun saveTestResult(saveTestResultModel: SaveTestResultEntity): Resource<ResultAfterSaveEntity>
    suspend fun getTestResults(testId: String): Flow<Resource<List<TestResultsGetEntity>>>
    suspend fun getInfoAboutTest(testId: String): Flow<Resource<TestInfoEntity>>
    suspend fun getQuestionsOfTest(testId: String): Resource<List<QuestionOfTestEntity>>
    suspend fun getTestResult(testResultId: String): Resource<TestResultsGetEntity>
}