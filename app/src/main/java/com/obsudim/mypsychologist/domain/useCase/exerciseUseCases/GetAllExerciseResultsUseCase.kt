package com.obsudim.mypsychologist.domain.useCase.exerciseUseCases

import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.ExerciseAllResultEntity
import com.obsudim.mypsychologist.domain.repository.retrofit.ExerciseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllExerciseResultsUseCase @Inject constructor(private val repository: ExerciseRepository) {
    suspend operator fun invoke(exerciseId: String): Flow<Resource<List<ExerciseAllResultEntity>>> =
        repository.getExerciseResults(exerciseId)
}