package com.example.mypsychologist.domain.useCase.diagnosticsUseCases

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.diagnosticEntity.ResultAfterSaveEntity
import com.example.mypsychologist.domain.entity.diagnosticEntity.SaveTestResultEntity
import com.example.mypsychologist.domain.repository.retrofit.TestsDiagnosticRepository
import javax.inject.Inject

class SaveResultTestUseCase @Inject constructor(
    private val repository: TestsDiagnosticRepository
) {
    suspend operator fun invoke(testResultEntity: SaveTestResultEntity): Resource<ResultAfterSaveEntity> =
        repository.saveTestResult(testResultEntity)
}