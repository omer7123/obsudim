package com.obsudim.mypsychologist.domain.useCase.exerciseUseCases.definitionProblemGroupExercise

import com.obsudim.mypsychologist.domain.entity.exerciseEntity.DefinitionProblemGroupExerciseEntity
import com.obsudim.mypsychologist.domain.repository.retrofit.ExerciseRepository
import javax.inject.Inject

class SaveDefinitionProblemGroupResultUseCase @Inject constructor(private val repository: ExerciseRepository) {
    suspend operator fun invoke(data: DefinitionProblemGroupExerciseEntity) = repository.saveDefinitionProblemGroupResult(data)
}