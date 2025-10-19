package com.obsudim.mypsychologist.domain.useCase.freeDiaryUseCase

import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.domain.entity.diaryEntity.MoodTrackerResultEntity
import com.obsudim.mypsychologist.domain.repository.retrofit.FreeDiaryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllMoodTrackersUseCase @Inject constructor(private val repository: FreeDiaryRepository) {
    suspend operator fun invoke(date: String): Flow<Resource<List<MoodTrackerResultEntity>>> =
        repository.getAllMoodTrackers(date)
}