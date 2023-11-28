package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.domain.entity.TestResultEntity
import com.example.mypsychologist.domain.repository.DiagnosticRepository
import java.util.Calendar
import javax.inject.Inject

class SaveCMQResultUseCase @Inject constructor(private val repository: DiagnosticRepository) {
    operator fun invoke(result: Int, conclusion: String) =
        repository.saveTestResult(
            TestResultEntity(
                result,
                conclusion,
                Calendar.getInstance().timeInMillis
            ),
            GetCMQTestUseCase.TEST_NAME
        )
}