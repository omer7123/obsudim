package com.example.mypsychologist.domain.useCase.exerciseUseCases

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.exerciseEntity.DailyTaskMarkIdEntity
import com.example.mypsychologist.domain.repository.retrofit.ExerciseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MarkAsCompleteExerciseUseCase @Inject constructor(private val repository: ExerciseRepository) {
    suspend operator fun invoke(markIdEntity: DailyTaskMarkIdEntity): Flow<Resource<String>> = repository.markAsCompleteDailyTask(markIdEntity)
}