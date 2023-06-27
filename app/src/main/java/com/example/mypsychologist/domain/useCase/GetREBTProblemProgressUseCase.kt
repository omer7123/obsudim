package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.domain.repository.ExercisesRepository
import javax.inject.Inject

class GetREBTProblemProgressUseCase @Inject constructor(private val repository: ExercisesRepository) {

    operator fun invoke(problem: String) =
        repository.getREBTProblemProgress(problem)
}