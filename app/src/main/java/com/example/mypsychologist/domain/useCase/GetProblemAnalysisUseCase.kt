package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.domain.entity.getMapOfFilledMembers
import com.example.mypsychologist.domain.repository.RebtRepository
import javax.inject.Inject

class GetProblemAnalysisUseCase @Inject constructor(private val repository: RebtRepository) {
    suspend operator fun invoke(): Map<String, String> =
        repository.getProblemAnalysis().getMapOfFilledMembers()
}