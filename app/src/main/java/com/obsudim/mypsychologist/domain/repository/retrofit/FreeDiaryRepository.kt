package com.obsudim.mypsychologist.domain.repository.retrofit

import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.domain.entity.diaryEntity.CalendarResponseEntity
import com.obsudim.mypsychologist.domain.entity.diaryEntity.EmojiEntity
import com.obsudim.mypsychologist.domain.entity.diaryEntity.FreeDiaryEntity
import com.obsudim.mypsychologist.domain.entity.diaryEntity.MoodTrackerRespEntity
import com.obsudim.mypsychologist.domain.entity.diaryEntity.MoodTrackerResultEntity
import com.obsudim.mypsychologist.domain.entity.diaryEntity.NewFreeDiaryWithDateEntity
import com.obsudim.mypsychologist.domain.entity.diaryEntity.SaveMoodEntity
import com.obsudim.mypsychologist.domain.entity.diaryEntity.SaveMoodWithDateEntity
import kotlinx.coroutines.flow.Flow

interface FreeDiaryRepository {
    suspend fun getFreeDiaries(): Resource<List<FreeDiaryEntity>>
    suspend fun addFreeDiary(freeDiary: NewFreeDiaryWithDateEntity): Resource<Unit>
    suspend fun getFreeDiariesByDay(day: String): Flow<Resource<List<FreeDiaryEntity>>>
    suspend fun saveMoodTracker(saveMoodEntity: SaveMoodEntity): Flow<Resource<MoodTrackerRespEntity>>
    suspend fun saveMoodTrackerWithDate(moodTrack: SaveMoodWithDateEntity): Flow<Resource<MoodTrackerRespEntity>>
    suspend fun getAllMoodTrackers(date: String): Flow<Resource<List<MoodTrackerResultEntity>>>
    suspend fun getDatesWithDiaries(month: Int): Flow<Resource<List<CalendarResponseEntity>>>
    suspend fun getAllEmojies(): Flow<Resource<List<EmojiEntity>>>

}