package com.obsudim.mypsychologist.domain.useCase.diagnosticsUseCases

import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.domain.entity.diagnosticEntity.TestResultsGetEntity
import com.obsudim.mypsychologist.domain.repository.retrofit.TestsDiagnosticRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTestResultsUseCase @Inject constructor(private val repository: TestsDiagnosticRepository) {

    suspend operator fun invoke(testId: String): Flow<Resource<List<TestResultsGetEntity>>> =
        repository.getTestResults(testId)
}