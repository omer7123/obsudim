package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.domain.entity.TestResultEntity
import com.example.mypsychologist.domain.repository.DiagnosticRepository
import java.util.*
import javax.inject.Inject

class SaveDepressionBeckResultUseCase @Inject constructor(
    private val repository: DiagnosticRepository
) {
    operator fun invoke(result: Int, conclusionId: Int) =
        repository.saveBeckDepression(
            TestResultEntity(
                result,
                conclusionId,
                Calendar.getInstance().timeInMillis
            )
        )
}