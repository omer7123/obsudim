package com.obsudim.mypsychologist.domain.useCase.exerciseUseCases

import com.obsudim.mypsychologist.domain.entity.exerciseEntity.ExerciseResultRequestEntity
import com.obsudim.mypsychologist.domain.repository.retrofit.ExerciseRepository
import javax.inject.Inject

class SaveExerciseResultUseCase @Inject constructor(private val repository: ExerciseRepository) {
    suspend operator fun invoke(result: ExerciseResultRequestEntity) = repository.saveExerciseResult(result)
}