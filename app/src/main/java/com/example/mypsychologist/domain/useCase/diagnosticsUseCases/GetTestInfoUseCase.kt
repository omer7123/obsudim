package com.example.mypsychologist.domain.useCase.diagnosticsUseCases

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.diagnosticEntity.TestInfoEntity
import com.example.mypsychologist.domain.repository.retrofit.TestsDiagnosticRepository
import javax.inject.Inject

class GetTestInfoUseCase @Inject constructor(private val repository: TestsDiagnosticRepository) {
    suspend operator fun invoke(testId: String): Resource<TestInfoEntity> =
        repository.getInfoAboutTest(testId)
}