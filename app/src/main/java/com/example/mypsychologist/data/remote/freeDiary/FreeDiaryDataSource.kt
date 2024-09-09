package com.example.mypsychologist.data.remote.freeDiary

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.model.FreeDiaryModel
import com.example.mypsychologist.data.model.MoodTrackerRespModel
import com.example.mypsychologist.data.model.NewFreeDiaryModel
import com.example.mypsychologist.data.model.SaveMoodModel

interface FreeDiaryDataSource {
    suspend fun getFreeDiaries(): Resource<List<FreeDiaryModel>>
    suspend fun addFreeDiary(freeDiary: NewFreeDiaryModel): Resource<String>
    suspend fun saveMoodTracker(saveMoodModel: SaveMoodModel): Resource<MoodTrackerRespModel>
}