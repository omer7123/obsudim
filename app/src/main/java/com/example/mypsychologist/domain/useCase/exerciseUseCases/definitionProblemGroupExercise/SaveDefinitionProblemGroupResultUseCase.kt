package com.example.mypsychologist.domain.useCase.exerciseUseCases.definitionProblemGroupExercise

import com.example.mypsychologist.domain.entity.exerciseEntity.DefinitionProblemGroupExerciseEntity
import com.example.mypsychologist.domain.repository.retrofit.ExerciseRepository
import javax.inject.Inject

class SaveDefinitionProblemGroupResultUseCase @Inject constructor(private val repository: ExerciseRepository) {
    suspend operator fun invoke(data: DefinitionProblemGroupExerciseEntity) = repository.saveDefinitionProblemGroupResult(data)
}