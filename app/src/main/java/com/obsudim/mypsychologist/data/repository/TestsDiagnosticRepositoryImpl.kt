package com.obsudim.mypsychologist.data.repository

import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.data.converters.toEntity
import com.obsudim.mypsychologist.data.converters.toModel
import com.obsudim.mypsychologist.data.remote.diagnostic.TestsDiagnosticDataSource
import com.obsudim.mypsychologist.di.ApiUrlProvider
import com.obsudim.mypsychologist.domain.entity.diagnosticEntity.QuestionOfTestEntity
import com.obsudim.mypsychologist.domain.entity.diagnosticEntity.ResultAfterSaveEntity
import com.obsudim.mypsychologist.domain.entity.diagnosticEntity.SaveTestResultEntity
import com.obsudim.mypsychologist.domain.entity.diagnosticEntity.TestEntity
import com.obsudim.mypsychologist.domain.entity.diagnosticEntity.TestInfoEntity
import com.obsudim.mypsychologist.domain.entity.diagnosticEntity.TestResultsGetEntity
import com.obsudim.mypsychologist.domain.repository.retrofit.TestsDiagnosticRepository
import javax.inject.Inject

class TestsDiagnosticRepositoryImpl @Inject constructor(
    private val dataSource: TestsDiagnosticDataSource,
    private val apiUrlProvider: ApiUrlProvider
) :
    TestsDiagnosticRepository {
    override suspend fun getAllTests(): Resource<List<TestEntity>> {
        return when (val result = dataSource.getAllTests()) {
            is Resource.Error -> Resource.Error(result.msg, null)
            Resource.Loading -> Resource.Loading
            is Resource.Success -> Resource.Success(result.data.map { it.toEntity(apiUrlProvider.url) })
        }
    }

    override suspend fun saveTestResult(saveTestResultModel: SaveTestResultEntity): Resource<ResultAfterSaveEntity> {
        return when (val result = dataSource.saveTestResult(saveTestResultModel.toModel())) {
            is Resource.Error -> Resource.Error(result.msg, null)
            Resource.Loading -> Resource.Loading
            is Resource.Success -> Resource.Success(result.data.toEntity())
        }
    }

    override suspend fun getTestResults(testId: String): Resource<List<TestResultsGetEntity>> {
        return when (val res = dataSource.getTestResults(testId)) {
            is Resource.Success -> Resource.Success(res.data.map { it.toEntity() })
            is Resource.Error -> Resource.Error(res.msg.toString(), null)
            Resource.Loading -> Resource.Loading
        }
    }

    override suspend fun getInfoAboutTest(testId: String): Resource<TestInfoEntity> {
        return when(val result = dataSource.getInfoAboutTest(testId)){
            is Resource.Error -> Resource.Error(result.msg.toString(), null)
            Resource.Loading -> Resource.Loading
            is Resource.Success -> Resource.Success(result.data.toEntity())
        }
    }

    override suspend fun getQuestionsOfTest(testId: String): Resource<List<QuestionOfTestEntity>> {
        return when(val res = dataSource.getQuestionsOfTest(testId)){
            is Resource.Error -> Resource.Error(res.msg, null)
            Resource.Loading -> Resource.Loading
            is Resource.Success -> Resource.Success(res.data.map { it.toEntity() })
        }
    }

    override suspend fun getTestResult(testResultId: String): Resource<TestResultsGetEntity> =
        when (val result = dataSource.getTestResult(testResultId)) {
            is Resource.Error -> Resource.Error(result.msg, null)
            is Resource.Loading -> Resource.Loading
            is Resource.Success -> Resource.Success(result.data.toEntity())
        }
}