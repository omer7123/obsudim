package com.example.mypsychologist.domain.repository.retrofit

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.model.MoodTrackerRespModel
import com.example.mypsychologist.data.model.SaveMoodModel
import com.example.mypsychologist.domain.entity.diaryEntity.FreeDiaryEntity
import com.example.mypsychologist.domain.entity.diaryEntity.MoodTrackerRespEntity
import com.example.mypsychologist.domain.entity.diaryEntity.NewFreeDiaryEntity
import com.example.mypsychologist.domain.entity.diaryEntity.SaveMoodEntity

interface FreeDiaryRepository {
    suspend fun getFreeDiaries(): Resource<List<FreeDiaryEntity>>
    suspend fun addFreeDiary(freeDiary: NewFreeDiaryEntity): Resource<String>
    suspend fun saveMoodTracker(saveMoodEntity: SaveMoodEntity): Resource<MoodTrackerRespEntity>

}