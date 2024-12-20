package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.domain.repository.RebtRepository
import javax.inject.Inject

class GetREBTProblemProgressUseCase @Inject constructor(private val repository: RebtRepository) {

    suspend operator fun invoke(problemId: String) =
        repository.getREBTProblemProgress(problemId)
}