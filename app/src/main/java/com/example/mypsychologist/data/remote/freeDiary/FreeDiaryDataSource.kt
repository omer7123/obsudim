package com.example.mypsychologist.data.remote.freeDiary

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.model.FreeDiaryModel
import com.example.mypsychologist.data.model.MoodTrackerPresentModel
import com.example.mypsychologist.data.model.MoodTrackerRespModel
import com.example.mypsychologist.data.model.NewFreeDiaryModel
import com.example.mypsychologist.data.model.NewFreeDiaryWithDateModel
import com.example.mypsychologist.data.model.SaveMoodModel
import com.example.mypsychologist.data.model.SaveMoodWithDateModel
import kotlinx.coroutines.flow.Flow

interface FreeDiaryDataSource {
    suspend fun getFreeDiaries(): Resource<List<FreeDiaryModel>>
    suspend fun addFreeDiary(freeDiary: NewFreeDiaryModel): Resource<String>
    suspend fun getFreeDiariesByDate(date: String): Flow<Resource<List<FreeDiaryModel>>>
    suspend fun saveMoodTracker(saveMoodModel: SaveMoodModel): Resource<MoodTrackerRespModel>
    suspend fun saveMoodTrackerWithDate(saveMoodModel: SaveMoodWithDateModel): Flow<Resource<MoodTrackerRespModel>>
    suspend fun addFreeDiaryWithDate(toNewFreeDiaryModel: NewFreeDiaryWithDateModel): Flow<Resource<String>>
    suspend fun getAllMoodTrackers(date: String): Flow<Resource<List<MoodTrackerPresentModel>>>
}