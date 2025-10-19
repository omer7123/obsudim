package com.obsudim.mypsychologist.domain.useCase.diagnosticsUseCases

import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.domain.entity.diagnosticEntity.TestInfoForPassingEntity
import com.obsudim.mypsychologist.domain.repository.retrofit.TestsDiagnosticRepository
import javax.inject.Inject

class GetQuestionsOfTestByIdUseCase @Inject constructor(private val repository: TestsDiagnosticRepository) {

    suspend operator fun invoke(testId: String): Resource<TestInfoForPassingEntity> =
        repository.getQuestionsOfTest(testId)
}