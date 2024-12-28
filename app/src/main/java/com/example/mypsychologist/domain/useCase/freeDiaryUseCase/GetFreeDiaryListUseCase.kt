package com.example.mypsychologist.domain.useCase.freeDiaryUseCase

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.diaryEntity.FreeDiaryEntity
import com.example.mypsychologist.domain.repository.retrofit.FreeDiaryRepository
import javax.inject.Inject

class GetFreeDiaryListUseCase @Inject constructor(private val repository: FreeDiaryRepository) {

    suspend operator fun invoke(): Resource<List<FreeDiaryEntity>> = repository.getFreeDiaries()
}