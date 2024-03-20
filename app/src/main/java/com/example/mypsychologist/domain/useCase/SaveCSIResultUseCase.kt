package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.domain.entity.CSIResultEntity
import com.example.mypsychologist.domain.entity.TestScalesResultEntity
import com.example.mypsychologist.domain.repository.DiagnosticRepository
import java.util.Calendar
import javax.inject.Inject

class SaveCSIResultUseCase @Inject constructor(private val repository: DiagnosticRepository) {
    operator fun invoke(result: CSIResultEntity) =
        repository.saveTestResult(
            TestScalesResultEntity(
                hashMapOf(
                    CSIResultEntity::problemResolutionStrategy.name to result.problemResolutionStrategy.first,
                    CSIResultEntity::strategyForSeekingSocialSupport.name to result.strategyForSeekingSocialSupport.first,
                    CSIResultEntity::avoidanceStrategy.name to result.avoidanceStrategy.first,
                ),
                Calendar.getInstance().timeInMillis
            ),
            GetCSITestUseCase.TEST_NAME
        )
}
