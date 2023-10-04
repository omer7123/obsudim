package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.domain.entity.TestResultEntity
import com.example.mypsychologist.domain.repository.DiagnosticRepository
import java.util.*
import javax.inject.Inject

class SaveDepressionBeckResultUseCase @Inject constructor(
    private val repository: DiagnosticRepository
) {
    operator fun invoke(result: Int, conclusion: String) =
        repository.saveBeckDepression(
            TestResultEntity(
                result,
                conclusion,
                Calendar.getInstance().timeInMillis
            ),
            GetDepressionBeckTestQuestionsUseCase.TEST_NAME
        )
}