package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.domain.entity.JASResultEntity
import com.example.mypsychologist.domain.entity.TestScalesResultEntity
import com.example.mypsychologist.domain.repository.DiagnosticRepository
import java.util.Calendar
import javax.inject.Inject

class SaveJASResultUseCase @Inject constructor(private val repository: DiagnosticRepository) {
    operator fun invoke(result: JASResultEntity) =
        repository.saveTestResult(
            TestScalesResultEntity(
                hashMapOf(
                    JASResultEntity::apatheticThoughts.name to result.apatheticThoughts.first,
                    JASResultEntity::apatheticThoughts.name to result.apatheticActions.first
                ),
                Calendar.getInstance().timeInMillis
            ),
            GetJASTestUseCase.TEST_NAME
        )
}
