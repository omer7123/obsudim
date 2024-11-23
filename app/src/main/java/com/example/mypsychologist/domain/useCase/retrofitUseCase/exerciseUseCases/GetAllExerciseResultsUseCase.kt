package com.example.mypsychologist.domain.useCase.retrofitUseCase.exerciseUseCases

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.exerciseEntity.ExerciseResultFromAPIEntity
import com.example.mypsychologist.domain.repository.retrofit.ExerciseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllExerciseResultsUseCase @Inject constructor(private val repository: ExerciseRepository) {
    suspend operator fun invoke(exerciseId: String): Flow<Resource<List<ExerciseResultFromAPIEntity>>> =
        repository.getExerciseResults(exerciseId)
}