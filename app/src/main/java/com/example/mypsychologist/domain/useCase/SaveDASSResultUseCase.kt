package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.domain.entity.DASSResultEntity
import com.example.mypsychologist.domain.entity.TestScalesResultEntity
import com.example.mypsychologist.domain.repository.DiagnosticRepository
import java.util.Calendar
import javax.inject.Inject

class SaveDASSResultUseCase @Inject constructor(private val repository: DiagnosticRepository) {
    operator fun invoke(result: DASSResultEntity) =
        repository.saveTestResult(
            TestScalesResultEntity(
                hashMapOf(
                    DASSResultEntity::stressScoreAndConclusion.name to result.stressScoreAndConclusion.first,
                    DASSResultEntity::anxietyScoreAndConclusion.name to result.anxietyScoreAndConclusion.first,
                    DASSResultEntity::depressionScoreAndConclusion.name to result.depressionScoreAndConclusion.first,
                ),
                Calendar.getInstance().timeInMillis
            ),
            GetDASSTestUseCase.TEST_NAME
        )
}