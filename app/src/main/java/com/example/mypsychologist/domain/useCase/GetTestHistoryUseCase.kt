package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.domain.repository.DiagnosticRepository
import javax.inject.Inject

class GetTestHistoryUseCase @Inject constructor(private val repository: DiagnosticRepository) {
    operator fun invoke(title: String) =
        repository.getTestResults(title)
}