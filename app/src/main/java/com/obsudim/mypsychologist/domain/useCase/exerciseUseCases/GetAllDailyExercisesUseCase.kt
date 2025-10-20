package com.obsudim.mypsychologist.domain.useCase.exerciseUseCases

import com.obsudim.mypsychologist.domain.repository.retrofit.ExerciseRepository
import javax.inject.Inject

class GetAllDailyExercisesUseCase @Inject constructor(private val repository: ExerciseRepository) {
    suspend operator fun invoke() = repository.getDailyExercises()
}