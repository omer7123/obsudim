package com.example.mypsychologist.data.repository

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.converters.toEntity
import com.example.mypsychologist.data.converters.toModel
import com.example.mypsychologist.data.remote.diagnostic.TestsDiagnosticDataSource
import com.example.mypsychologist.di.ApiUrlProvider
import com.example.mypsychologist.domain.entity.diagnosticEntity.ResultAfterSaveEntity
import com.example.mypsychologist.domain.entity.diagnosticEntity.SaveTestResultEntity
import com.example.mypsychologist.domain.entity.diagnosticEntity.TestEntity
import com.example.mypsychologist.domain.entity.diagnosticEntity.TestInfoEntity
import com.example.mypsychologist.domain.entity.diagnosticEntity.TestInfoForPassingEntity
import com.example.mypsychologist.domain.entity.diagnosticEntity.TestResultsGetEntity
import com.example.mypsychologist.domain.repository.retrofit.TestsDiagnosticRepository
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

    override suspend fun getQuestionsOfTest(testId: String): Resource<TestInfoForPassingEntity> {
        return when(val res = dataSource.getQuestionsOfTest(testId)){
            is Resource.Error -> Resource.Error(res.msg, null)
            Resource.Loading -> Resource.Loading
            is Resource.Success -> Resource.Success(res.data.toEntity())
        }
    }

    override suspend fun getTestResult(testResultId: String): Resource<TestResultsGetEntity> =
        when (val result = dataSource.getTestResult(testResultId)) {
            is Resource.Error -> Resource.Error(result.msg, null)
            is Resource.Loading -> Resource.Loading
            is Resource.Success -> Resource.Success(result.data.toEntity())
        }
}