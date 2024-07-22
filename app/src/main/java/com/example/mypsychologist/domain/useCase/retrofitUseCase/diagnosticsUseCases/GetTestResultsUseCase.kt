package com.example.mypsychologist.domain.useCase.retrofitUseCase.diagnosticsUseCases

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.diagnosticEntity.TestResultsGetEntity
import com.example.mypsychologist.domain.repository.retrofit.TestsDiagnosticRepository
import javax.inject.Inject

class GetTestResultsUseCase @Inject constructor(private val repository: TestsDiagnosticRepository) {

    suspend operator fun invoke(testId: String): Resource<List<TestResultsGetEntity>> = repository.getTestResults(testId)
}