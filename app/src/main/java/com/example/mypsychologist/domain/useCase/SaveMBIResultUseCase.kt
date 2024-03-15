package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.domain.entity.MBIResultEntity
import com.example.mypsychologist.domain.entity.TestScalesResultEntity
import com.example.mypsychologist.domain.repository.DiagnosticRepository
import java.util.Calendar
import javax.inject.Inject

class SaveMBIResultUseCase @Inject constructor(private val repository: DiagnosticRepository) {
    operator fun invoke(result: MBIResultEntity) =
        repository.saveTestResult(
            TestScalesResultEntity(
                hashMapOf(
                    MBIResultEntity::emotionalExhaustion.name to result.emotionalExhaustion.first,
                    MBIResultEntity::depersonalization.name to result.depersonalization.first,
                    MBIResultEntity::reductionOfPersonalAchievements.name to result.reductionOfPersonalAchievements.first,
                ),
                Calendar.getInstance().timeInMillis
            ),
            GetMBITestUseCase.TEST_NAME
        )
}


