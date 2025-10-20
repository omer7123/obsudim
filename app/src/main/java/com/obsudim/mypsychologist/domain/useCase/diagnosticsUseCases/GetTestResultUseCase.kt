package com.obsudim.mypsychologist.domain.useCase.diagnosticsUseCases

import com.obsudim.mypsychologist.domain.repository.retrofit.TestsDiagnosticRepository
import javax.inject.Inject

class GetTestResultUseCase @Inject constructor(private val repository: TestsDiagnosticRepository) {
    suspend operator fun invoke(testResultId: String) =
        repository.getTestResult(testResultId)
}