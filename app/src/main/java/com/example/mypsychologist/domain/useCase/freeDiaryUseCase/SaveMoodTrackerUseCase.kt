package com.example.mypsychologist.domain.useCase.freeDiaryUseCase

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.diaryEntity.MoodTrackerRespEntity
import com.example.mypsychologist.domain.entity.diaryEntity.SaveMoodEntity
import com.example.mypsychologist.domain.repository.retrofit.FreeDiaryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveMoodTrackerUseCase @Inject constructor(private val repository: FreeDiaryRepository) {
    suspend operator fun invoke(saveMoodEntity: SaveMoodEntity): Flow<Resource<MoodTrackerRespEntity>> =
        repository.saveMoodTracker(saveMoodEntity)
}