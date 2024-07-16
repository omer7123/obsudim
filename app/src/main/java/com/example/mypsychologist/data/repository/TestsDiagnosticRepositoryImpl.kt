package com.example.mypsychologist.data.repository

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.converters.toEntity
import com.example.mypsychologist.data.remote.diagnostic.TestsDiagnosticDataSource
import com.example.mypsychologist.domain.entity.diagnosticEntity.TestEntity
import com.example.mypsychologist.domain.repository.retrofit.TestsDiagnosticRepository
import javax.inject.Inject

class TestsDiagnosticRepositoryImpl @Inject constructor(private val dataSource: TestsDiagnosticDataSource): TestsDiagnosticRepository {
    override suspend fun getAllTests(): Resource<List<TestEntity>> {
        return when(val result = dataSource.getAllTests()){
            is Resource.Error -> Resource.Error(result.msg, null)
            Resource.Loading -> Resource.Loading
            is Resource.Success -> Resource.Success(result.data.map {it.toEntity()})
        }
    }
}