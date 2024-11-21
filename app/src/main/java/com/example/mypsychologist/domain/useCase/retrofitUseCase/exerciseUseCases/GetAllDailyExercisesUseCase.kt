package com.example.mypsychologist.domain.useCase.retrofitUseCase.exerciseUseCases

import com.example.mypsychologist.domain.repository.retrofit.ExerciseRepository
import javax.inject.Inject

class GetAllDailyExercisesUseCase @Inject constructor(private val repository: ExerciseRepository) {
    suspend operator fun invoke() = repository.getDailyExercises()
}