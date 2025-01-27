package com.example.mypsychologist.domain.useCase.freeDiaryUseCase

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.diaryEntity.MoodTrackerResultEntity
import com.example.mypsychologist.domain.repository.retrofit.FreeDiaryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllMoodTrackersUseCase @Inject constructor(private val repository: FreeDiaryRepository) {
    suspend operator fun invoke(date: String): Flow<Resource<List<MoodTrackerResultEntity>>> =
        repository.getAllMoodTrackers(date)
}