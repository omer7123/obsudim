package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.ProblemAnalysisEntity
import com.example.mypsychologist.domain.repository.RebtRepository
import javax.inject.Inject

class SaveProblemAnalysisUseCase @Inject constructor(private val repository: RebtRepository) {
    suspend operator fun invoke(analysis: ProblemAnalysisEntity): List<Resource<String>> =
        repository.saveAnalysis(analysis)
}