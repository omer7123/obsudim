package com.example.mypsychologist.domain.repository

import com.example.mypsychologist.domain.entity.TestResultEntity
import com.example.mypsychologist.domain.entity.TestScalesResultEntity

interface DiagnosticRepository {
    fun saveTestResult(result: TestResultEntity, testTitle: String): Boolean
    fun saveTestResult(result: TestScalesResultEntity, testTitle: String): Boolean
    suspend fun getTestResults(title: String): List<TestResultEntity>

    suspend fun getTestResultsFor(clientId: String, title: String): List<TestResultEntity>
}