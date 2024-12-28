package com.example.mypsychologist.domain.useCase.exerciseUseCases

import com.example.mypsychologist.domain.entity.exerciseEntity.ExerciseResultRequestEntity
import com.example.mypsychologist.domain.repository.retrofit.ExerciseRepository
import javax.inject.Inject

class SaveExerciseResultUseCase @Inject constructor(private val repository: ExerciseRepository) {
    suspend operator fun invoke(result: ExerciseResultRequestEntity) = repository.saveExerciseResult(result)
}