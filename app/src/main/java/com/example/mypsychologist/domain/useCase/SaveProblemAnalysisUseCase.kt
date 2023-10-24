package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.domain.entity.ProblemAnalysisEntity
import com.example.mypsychologist.domain.repository.RebtRepository
import javax.inject.Inject

class SaveProblemAnalysisUseCase @Inject constructor(private val repository: RebtRepository) {
    suspend operator fun invoke(problemId: String, analysis: ProblemAnalysisEntity) =
        repository.saveAnalysis(problemId, analysis)
}