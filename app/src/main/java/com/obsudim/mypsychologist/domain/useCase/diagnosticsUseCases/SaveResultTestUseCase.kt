package com.obsudim.mypsychologist.domain.useCase.diagnosticsUseCases

import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.domain.entity.diagnosticEntity.ResultAfterSaveEntity
import com.obsudim.mypsychologist.domain.entity.diagnosticEntity.SaveTestResultEntity
import com.obsudim.mypsychologist.domain.repository.retrofit.TestsDiagnosticRepository
import javax.inject.Inject

class SaveResultTestUseCase @Inject constructor(
    private val repository: TestsDiagnosticRepository
) {
    suspend operator fun invoke(testResultEntity: SaveTestResultEntity): Resource<ResultAfterSaveEntity> =
        repository.saveTestResult(testResultEntity)
}