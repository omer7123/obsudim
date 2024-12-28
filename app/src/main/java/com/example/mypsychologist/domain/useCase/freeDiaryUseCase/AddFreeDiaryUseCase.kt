package com.example.mypsychologist.domain.useCase.freeDiaryUseCase

import com.example.mypsychologist.domain.entity.diaryEntity.NewFreeDiaryEntity
import com.example.mypsychologist.domain.repository.retrofit.FreeDiaryRepository
import javax.inject.Inject

class AddFreeDiaryUseCase @Inject constructor(private val repository: FreeDiaryRepository) {
    suspend operator fun invoke(freeDiary: NewFreeDiaryEntity) =
        repository.addFreeDiary(freeDiary)
}