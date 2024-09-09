package com.example.mypsychologist.data.remote.freeDiary

import com.example.mypsychologist.core.BaseDataSource
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.model.FreeDiaryModel
import com.example.mypsychologist.data.model.MoodTrackerRespModel
import com.example.mypsychologist.data.model.NewFreeDiaryModel
import com.example.mypsychologist.data.model.SaveMoodModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FreeDiaryDataSourceImpl @Inject constructor(private val api: FreeDiaryService) :
    FreeDiaryDataSource, BaseDataSource() {

    override suspend fun getFreeDiaries(): Resource<List<FreeDiaryModel>> = getResult {
        withContext(Dispatchers.IO) {
            api.getFreeDiaryList()
        }
    }

    override suspend fun addFreeDiary(freeDiary: NewFreeDiaryModel): Resource<String> = getResult {
        withContext(Dispatchers.IO) {
            api.addFreeDiary(freeDiary = freeDiary)
        }
    }

    override suspend fun saveMoodTracker(saveMoodModel: SaveMoodModel): Resource<MoodTrackerRespModel> =
        getResult {
            api.saveMoodTracker(saveMoodModel)
        }
}