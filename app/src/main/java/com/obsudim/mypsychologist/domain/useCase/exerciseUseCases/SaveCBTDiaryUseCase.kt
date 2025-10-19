package com.obsudim.mypsychologist.domain.useCase.exerciseUseCases

import com.obsudim.mypsychologist.domain.entity.ThoughtDiaryEntity
import com.obsudim.mypsychologist.domain.repository.retrofit.ExerciseRepository
import javax.inject.Inject

class SaveCBTDiaryUseCase @Inject constructor(private val repository: ExerciseRepository) {
    suspend operator fun invoke(result: ThoughtDiaryEntity) = repository.saveCBTDiary(result)
}