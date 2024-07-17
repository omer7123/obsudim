package com.example.mypsychologist.domain.repository.retrofit

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.diagnosticEntity.TestEntity

interface TestsDiagnosticRepository {
    suspend fun getAllTests(): Resource<List<TestEntity>>
}