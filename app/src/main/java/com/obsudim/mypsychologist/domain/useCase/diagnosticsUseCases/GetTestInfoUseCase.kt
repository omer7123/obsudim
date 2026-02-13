package com.obsudim.mypsychologist.domain.useCase.diagnosticsUseCases

import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.domain.entity.diagnosticEntity.TestInfoEntity
import com.obsudim.mypsychologist.domain.repository.retrofit.TestsDiagnosticRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTestInfoUseCase @Inject constructor(private val repository: TestsDiagnosticRepository) {
    suspend operator fun invoke(testId: String): Flow<Resource<TestInfoEntity>> =
        repository.getInfoAboutTest(testId)
}