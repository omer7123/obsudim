package com.obsudim.mypsychologist.domain.useCase.exerciseUseCases

import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.ExerciseResultRequestEntity
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.SaveExerciseResultResponseEntity
import com.obsudim.mypsychologist.domain.repository.retrofit.ExerciseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveExerciseResultUseCase @Inject constructor(private val repository: ExerciseRepository) {
    suspend operator fun invoke(result: ExerciseResultRequestEntity): Flow<Resource<SaveExerciseResultResponseEntity>> = repository.saveExerciseResult(result)
}