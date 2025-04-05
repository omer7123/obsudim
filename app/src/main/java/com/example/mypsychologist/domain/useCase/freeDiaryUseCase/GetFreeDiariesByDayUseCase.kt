package com.example.mypsychologist.domain.useCase.freeDiaryUseCase

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.diaryEntity.FreeDiaryEntity
import com.example.mypsychologist.domain.repository.retrofit.FreeDiaryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFreeDiariesByDayUseCase @Inject constructor(private val repository: FreeDiaryRepository) {
    suspend operator fun invoke(date: String): Flow<Resource<List<FreeDiaryEntity>>> =
        repository.getFreeDiariesByDay(date)
}