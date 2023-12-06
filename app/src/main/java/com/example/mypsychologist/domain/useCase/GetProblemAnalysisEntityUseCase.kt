package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.domain.entity.getMapOfFilledMembers
import com.example.mypsychologist.domain.repository.RebtRepository
import javax.inject.Inject

class GetProblemAnalysisEntityUseCase  @Inject constructor(private val repository: RebtRepository) {
    suspend operator fun invoke() =
        repository.getProblemAnalysis()
}