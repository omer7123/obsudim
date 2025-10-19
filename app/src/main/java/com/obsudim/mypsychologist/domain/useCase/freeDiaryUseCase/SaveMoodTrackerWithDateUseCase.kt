package com.obsudim.mypsychologist.domain.useCase.freeDiaryUseCase

import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.domain.entity.diaryEntity.MoodTrackerRespEntity
import com.obsudim.mypsychologist.domain.entity.diaryEntity.SaveMoodWithDateEntity
import com.obsudim.mypsychologist.domain.repository.retrofit.FreeDiaryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveMoodTrackerWithDateUseCase @Inject constructor(private val repository: FreeDiaryRepository) {
    suspend operator fun invoke(moodTrack: SaveMoodWithDateEntity): Flow<Resource<MoodTrackerRespEntity>> = repository.saveMoodTrackerWithDate(moodTrack)
}