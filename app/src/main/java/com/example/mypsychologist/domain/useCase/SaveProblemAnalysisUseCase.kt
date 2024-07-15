package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.domain.entity.ProblemAnalysisEntity
import com.example.mypsychologist.domain.repository.RebtRepository
import javax.inject.Inject

class SaveProblemAnalysisUseCase @Inject constructor(private val repository: RebtRepository) {
    suspend operator fun invoke(analysis: ProblemAnalysisEntity) =
        repository.saveAnalysis(analysis)
}