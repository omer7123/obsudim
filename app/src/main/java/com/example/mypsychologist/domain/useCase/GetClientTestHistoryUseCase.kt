package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.domain.repository.DiagnosticRepository
import javax.inject.Inject

class GetClientTestHistoryUseCase @Inject constructor(private val repository: DiagnosticRepository) {
    suspend operator fun invoke(clientId: String, testTitle: String) =
        repository.getTestResultsFor(clientId, testTitle)
}