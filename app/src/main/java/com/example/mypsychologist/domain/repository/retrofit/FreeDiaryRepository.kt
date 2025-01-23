package com.example.mypsychologist.domain.repository.retrofit

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.diaryEntity.FreeDiaryEntity
import com.example.mypsychologist.domain.entity.diaryEntity.MoodTrackerRespEntity
import com.example.mypsychologist.domain.entity.diaryEntity.NewFreeDiaryEntity
import com.example.mypsychologist.domain.entity.diaryEntity.NewFreeDiaryWithDateEntity
import com.example.mypsychologist.domain.entity.diaryEntity.SaveMoodEntity
import kotlinx.coroutines.flow.Flow

interface FreeDiaryRepository {
    suspend fun getFreeDiaries(): Resource<List<FreeDiaryEntity>>
    suspend fun addFreeDiary(freeDiary: NewFreeDiaryEntity): Resource<String>
    suspend fun getFreeDiariesByDay(day: String): Flow<Resource<List<FreeDiaryEntity>>>
    suspend fun saveMoodTracker(saveMoodEntity: SaveMoodEntity): Resource<MoodTrackerRespEntity>
    suspend fun addFreeDiaryWithDate(data: NewFreeDiaryWithDateEntity): Flow<Resource<String>>

}