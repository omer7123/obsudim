package com.example.mypsychologist.domain.repository.retrofit

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.diaryEntity.CalendarResponseEntity
import com.example.mypsychologist.domain.entity.diaryEntity.FreeDiaryEntity
import com.example.mypsychologist.domain.entity.diaryEntity.MoodTrackerRespEntity
import com.example.mypsychologist.domain.entity.diaryEntity.MoodTrackerResultEntity
import com.example.mypsychologist.domain.entity.diaryEntity.NewFreeDiaryWithDateEntity
import com.example.mypsychologist.domain.entity.diaryEntity.SaveMoodEntity
import com.example.mypsychologist.domain.entity.diaryEntity.SaveMoodWithDateEntity
import kotlinx.coroutines.flow.Flow

interface FreeDiaryRepository {
    suspend fun getFreeDiaries(): Resource<List<FreeDiaryEntity>>
    suspend fun addFreeDiary(freeDiary: NewFreeDiaryWithDateEntity): Resource<Unit>
    suspend fun getFreeDiariesByDay(day: String): Flow<Resource<List<FreeDiaryEntity>>>
    suspend fun saveMoodTracker(saveMoodEntity: SaveMoodEntity): Resource<MoodTrackerRespEntity>
    suspend fun saveMoodTrackerWithDate(moodTrack: SaveMoodWithDateEntity): Flow<Resource<MoodTrackerRespEntity>>
    suspend fun getAllMoodTrackers(date: String): Flow<Resource<List<MoodTrackerResultEntity>>>
    suspend fun getDatesWithDiaries(month: Int): Flow<Resource<List<CalendarResponseEntity>>>

}