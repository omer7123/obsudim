package com.example.mypsychologist.data.repository

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.converters.toEntity
import com.example.mypsychologist.data.converters.toModel
import com.example.mypsychologist.data.remote.diagnostic.TestsDiagnosticDataSource
import com.example.mypsychologist.domain.entity.diagnosticEntity.SaveTestResultEntity
import com.example.mypsychologist.domain.entity.diagnosticEntity.TestEntity
import com.example.mypsychologist.domain.entity.diagnosticEntity.TestResultsGetEntity
import com.example.mypsychologist.domain.repository.retrofit.TestsDiagnosticRepository
import javax.inject.Inject

class TestsDiagnosticRepositoryImpl @Inject constructor(
    private val dataSource: TestsDiagnosticDataSource
) :
    TestsDiagnosticRepository {
    override suspend fun getAllTests(): Resource<List<TestEntity>> {
        return when (val result = dataSource.getAllTests()) {
            is Resource.Error -> Resource.Error(result.msg, null)
            Resource.Loading -> Resource.Loading
            is Resource.Success -> Resource.Success(result.data.map { it.toEntity() })
        }
    }

    override suspend fun saveTestResult(saveTestResultModel: SaveTestResultEntity): Resource<String> {
        return when (val result = dataSource.saveTestResult(saveTestResultModel.toModel())) {
            is Resource.Error -> Resource.Error(result.msg, null)
            Resource.Loading -> Resource.Loading
            is Resource.Success -> Resource.Success(result.data)
        }
    }

    override suspend fun getTestResults(testId: String): Resource<List<TestResultsGetEntity>> {
        return when (val res = dataSource.getTestResults(testId)) {
            is Resource.Success -> Resource.Success(res.data.map { it.toEntity() })
            is Resource.Error -> Resource.Error(res.msg.toString(), null)
            Resource.Loading -> Resource.Loading
        }
    }
}