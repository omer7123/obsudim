package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.domain.entity.BeliefAnalysisEntity
import com.example.mypsychologist.domain.repository.RebtRepository
import javax.inject.Inject

class SaveBeliefAnalysisUseCase @Inject constructor(private val repository: RebtRepository) {
    suspend operator fun invoke(it: BeliefAnalysisEntity, type: String) =
        repository.saveBeliefAnalysis(it, type)
}