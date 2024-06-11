package com.example.mypsychologist.domain.useCase.retrofitUseCase.freeDiaryUseCase

import com.example.mypsychologist.domain.entity.FreeDiary
import com.example.mypsychologist.domain.repository.retrofit.FreeDiaryRepository
import javax.inject.Inject

class AddFreeDiaryUseCase @Inject constructor(private val repository: FreeDiaryRepository) {
    suspend operator fun invoke(freeDiary: FreeDiary) =
        repository.addFreeDiary(freeDiary)
}