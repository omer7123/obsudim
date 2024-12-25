package com.example.mypsychologist.domain.useCase.retrofitUseCase.exerciseUseCases

import com.example.mypsychologist.domain.entity.ThoughtDiaryEntity
import com.example.mypsychologist.domain.repository.retrofit.CbtRepository
import javax.inject.Inject

class SaveThoughtDiaryUseCase @Inject constructor(private val repository: CbtRepository) {
    suspend operator fun invoke(it: ThoughtDiaryEntity) =
        repository.saveThoughtDiary(it)
}

