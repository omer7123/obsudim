package com.example.mypsychologist.domain.repository

import com.example.mypsychologist.domain.entity.TestResultEntity

interface DiagnosticRepository {
    fun saveBeckDepression(result: TestResultEntity, testTitle: String): Boolean
    suspend fun getTestResults(title: String): List<TestResultEntity>
}