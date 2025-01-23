package com.example.mypsychologist.data.remote.freeDiary

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.model.FreeDiaryModel
import com.example.mypsychologist.data.model.MoodTrackerRespModel
import com.example.mypsychologist.data.model.NewFreeDiaryModel
import com.example.mypsychologist.data.model.SaveMoodModel
import kotlinx.coroutines.flow.Flow

interface FreeDiaryDataSource {
    suspend fun getFreeDiaries(): Resource<List<FreeDiaryModel>>
    suspend fun addFreeDiary(freeDiary: NewFreeDiaryModel): Resource<String>
    suspend fun getFreeDiariesByDate(date: String): Flow<Resource<List<FreeDiaryModel>>>
    suspend fun saveMoodTracker(saveMoodModel: SaveMoodModel): Resource<MoodTrackerRespModel>
}