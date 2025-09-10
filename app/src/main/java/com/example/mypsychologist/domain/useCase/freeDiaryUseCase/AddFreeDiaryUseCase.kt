package com.example.mypsychologist.domain.useCase.freeDiaryUseCase

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.diaryEntity.NewFreeDiaryWithDateEntity
import com.example.mypsychologist.domain.repository.retrofit.FreeDiaryRepository
import javax.inject.Inject

class AddFreeDiaryUseCase @Inject constructor(private val repository: FreeDiaryRepository) {
    suspend operator fun invoke(freeDiary: NewFreeDiaryWithDateEntity): Resource<Unit> =
        repository.addFreeDiary(freeDiary)
}