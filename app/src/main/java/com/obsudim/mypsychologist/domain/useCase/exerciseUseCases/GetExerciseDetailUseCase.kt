package com.obsudim.mypsychologist.domain.useCase.exerciseUseCases

import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.ExerciseDetailEntity
import com.obsudim.mypsychologist.domain.repository.retrofit.ExerciseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetExerciseDetailUseCase @Inject constructor(private val repository: ExerciseRepository) {
    suspend operator fun invoke(id: String): Flow<Resource<ExerciseDetailEntity>> =
        repository.getExerciseDetail(id)
}