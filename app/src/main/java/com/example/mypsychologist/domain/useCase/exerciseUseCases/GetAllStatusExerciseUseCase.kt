package com.example.mypsychologist.domain.useCase.exerciseUseCases

import com.example.mypsychologist.domain.repository.retrofit.ExerciseRepository
import javax.inject.Inject

class GetAllStatusExerciseUseCase @Inject constructor(private val repository: ExerciseRepository){
    suspend operator fun invoke() = repository.getAllStatusExercise()
}