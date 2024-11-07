package com.example.mypsychologist.domain.useCase.retrofitUseCase.exerciseUseCases

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.exerciseEntity.ExerciseEntity
import com.example.mypsychologist.domain.repository.retrofit.ExerciseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllExercisesUseCase @Inject constructor(private val repository: ExerciseRepository) {
    suspend operator fun invoke(): Flow<Resource<List<ExerciseEntity>>> = repository.getAllExercises()
}