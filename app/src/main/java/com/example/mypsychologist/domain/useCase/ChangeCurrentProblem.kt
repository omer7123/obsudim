package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.domain.repository.RebtRepository
import javax.inject.Inject

class ChangeCurrentProblem @Inject constructor(private val repository: RebtRepository) {
    suspend operator fun invoke(problemId: String) =
        repository.saveCurrentProblem(problemId)
}