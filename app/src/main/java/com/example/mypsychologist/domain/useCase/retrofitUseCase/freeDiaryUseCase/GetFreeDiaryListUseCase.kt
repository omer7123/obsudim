package com.example.mypsychologist.domain.useCase.retrofitUseCase.freeDiaryUseCase

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.FreeDiary
import com.example.mypsychologist.domain.repository.retrofit.FreeDiaryRepository
import javax.inject.Inject

class GetFreeDiaryListUseCase @Inject constructor(private val repository: FreeDiaryRepository) {

    suspend operator fun invoke(): Resource<List<FreeDiary>> =
        repository.getFreeDiaryList()
}