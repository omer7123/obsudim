package com.example.mypsychologist.domain.useCase.retrofitUseCase.diagnosticsUseCases

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.diagnosticEntity.QuestionOfTestEntity
import com.example.mypsychologist.domain.repository.retrofit.TestsDiagnosticRepository
import javax.inject.Inject

class GetQuestionsOfTestByIdUseCase @Inject constructor(private val repository: TestsDiagnosticRepository) {

    suspend operator fun invoke(testId: String): Resource<List<QuestionOfTestEntity>> =
        repository.getQuestionsOfTest(testId)
}