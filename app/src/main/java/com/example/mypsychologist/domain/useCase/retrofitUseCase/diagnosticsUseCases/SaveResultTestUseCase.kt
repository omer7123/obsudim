package com.example.mypsychologist.domain.useCase.retrofitUseCase.diagnosticsUseCases

import com.example.mypsychologist.domain.entity.diagnosticEntity.SaveTestResultEntity
import com.example.mypsychologist.domain.repository.retrofit.TestsDiagnosticRepository
import javax.inject.Inject

class SaveResultTestUseCase @Inject constructor(
    private val repository: TestsDiagnosticRepository
) {
    suspend operator fun invoke(testResultEntity: SaveTestResultEntity) =
        repository.saveTestResult(testResultEntity)
}