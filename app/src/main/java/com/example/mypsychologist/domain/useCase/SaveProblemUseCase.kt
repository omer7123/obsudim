package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.domain.entity.ProblemEntity
import com.example.mypsychologist.domain.repository.RebtRepository
import javax.inject.Inject

class SaveProblemUseCase @Inject constructor(private val repository: RebtRepository) {
    suspend operator fun invoke(problemEntity: ProblemEntity) =
        repository.saveProblem(problemEntity)
}