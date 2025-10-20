package com.obsudim.mypsychologist.domain.useCase.freeDiaryUseCase

import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.domain.entity.diaryEntity.FreeDiaryEntity
import com.obsudim.mypsychologist.domain.repository.retrofit.FreeDiaryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFreeDiariesByDayUseCase @Inject constructor(private val repository: FreeDiaryRepository) {
    suspend operator fun invoke(date: String): Flow<Resource<List<FreeDiaryEntity>>> =
        repository.getFreeDiariesByDay(date)
}