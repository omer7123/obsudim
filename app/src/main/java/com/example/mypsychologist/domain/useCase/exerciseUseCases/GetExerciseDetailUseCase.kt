package com.example.mypsychologist.domain.useCase.exerciseUseCases

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.exerciseEntity.ExerciseDetailEntity
import com.example.mypsychologist.domain.repository.retrofit.ExerciseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetExerciseDetailUseCase @Inject constructor(private val repository: ExerciseRepository) {
    suspend operator fun invoke(id: String): Flow<Resource<ExerciseDetailEntity>> =
        repository.getExerciseDetail(id)
}