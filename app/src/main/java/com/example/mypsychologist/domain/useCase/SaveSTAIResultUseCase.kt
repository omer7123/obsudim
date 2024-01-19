package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.domain.entity.STAIResultEntity
import com.example.mypsychologist.domain.entity.TestScalesResultEntity
import com.example.mypsychologist.domain.repository.DiagnosticRepository
import java.util.Calendar
import javax.inject.Inject

class SaveSTAIResultUseCase @Inject constructor(private val repository: DiagnosticRepository) {
    operator fun invoke(result: STAIResultEntity) =
        repository.saveTestResult(
            TestScalesResultEntity(
                hashMapOf(
                    STAIResultEntity::situationalAnxiety.name to result.situationalAnxiety.first,
                    STAIResultEntity::personalAnxiety.name to result.personalAnxiety.first
                ),
                Calendar.getInstance().timeInMillis
            ),
            GetSTAITestUseCase.TEST_NAME
        )
}