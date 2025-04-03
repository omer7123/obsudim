package com.example.mypsychologist.domain.useCase.exerciseUseCases

import com.example.mypsychologist.domain.entity.ThoughtDiaryEntity
import com.example.mypsychologist.domain.repository.retrofit.ExerciseRepository
import javax.inject.Inject

class SaveCBTDiaryUseCase @Inject constructor(private val repository: ExerciseRepository) {
    suspend operator fun invoke(result: ThoughtDiaryEntity) = repository.saveCBTDiary(result)
}