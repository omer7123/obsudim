package com.obsudim.mypsychologist.domain.useCase.exerciseUseCases.definitionProblemGroupExercise

import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.RecordExerciseEntity
import com.obsudim.mypsychologist.domain.repository.retrofit.ExerciseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllDPGResultsUseCase @Inject constructor(private val repository: ExerciseRepository) {
    suspend operator fun invoke(): Flow<Resource<List<RecordExerciseEntity>>> = repository.getAllDPGResults()
}