package com.example.mypsychologist.domain.useCase.freeDiaryUseCase

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.diaryEntity.CalendarResponseEntity
import com.example.mypsychologist.domain.repository.retrofit.FreeDiaryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDatesWithDiariesUseCase @Inject constructor(private val repository: FreeDiaryRepository) {
    suspend operator fun invoke(month: Int): Flow<Resource<List<CalendarResponseEntity>>> =
        repository.getDatesWithDiaries(month)
}