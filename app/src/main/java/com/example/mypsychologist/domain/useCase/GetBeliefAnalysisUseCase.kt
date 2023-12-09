package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.domain.repository.RebtRepository
import javax.inject.Inject

class GetBeliefAnalysisUseCase @Inject constructor(private val repository: RebtRepository) {
    suspend operator fun invoke(type: String) =
        repository.getBeliefAnalysis(type)
}