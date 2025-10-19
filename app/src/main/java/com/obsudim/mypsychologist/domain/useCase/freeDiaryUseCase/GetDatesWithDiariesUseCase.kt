package com.obsudim.mypsychologist.domain.useCase.freeDiaryUseCase

import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.domain.entity.diaryEntity.CalendarResponseEntity
import com.obsudim.mypsychologist.domain.repository.retrofit.FreeDiaryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDatesWithDiariesUseCase @Inject constructor(private val repository: FreeDiaryRepository) {
    suspend operator fun invoke(month: Int): Flow<Resource<List<CalendarResponseEntity>>> =
        repository.getDatesWithDiaries(month)
}