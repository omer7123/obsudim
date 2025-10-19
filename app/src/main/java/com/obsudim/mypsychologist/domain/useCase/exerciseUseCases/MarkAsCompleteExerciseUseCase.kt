package com.obsudim.mypsychologist.domain.useCase.exerciseUseCases

import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.DailyTaskMarkIdEntity
import com.obsudim.mypsychologist.domain.repository.retrofit.ExerciseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MarkAsCompleteExerciseUseCase @Inject constructor(private val repository: ExerciseRepository) {
    suspend operator fun invoke(markIdEntity: DailyTaskMarkIdEntity): Flow<Resource<String>> = repository.markAsCompleteDailyTask(markIdEntity)
}