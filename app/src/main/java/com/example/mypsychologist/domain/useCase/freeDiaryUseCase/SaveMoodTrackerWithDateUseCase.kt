package com.example.mypsychologist.domain.useCase.freeDiaryUseCase

import com.example.mypsychologist.domain.entity.diaryEntity.SaveMoodWithDateEntity
import com.example.mypsychologist.domain.repository.retrofit.FreeDiaryRepository
import javax.inject.Inject

class SaveMoodTrackerWithDateUseCase @Inject constructor(private val repository: FreeDiaryRepository) {
    suspend operator fun invoke(moodTrack: SaveMoodWithDateEntity) = repository.saveMoodTrackerWithDate(moodTrack)
}