package com.example.mypsychologist.data.remote.freeDiary

import com.example.mypsychologist.core.BaseDataSource
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.model.CalendarResponseModel
import com.example.mypsychologist.data.model.FreeDiaryModel
import com.example.mypsychologist.data.model.MoodTrackerPresentModel
import com.example.mypsychologist.data.model.MoodTrackerRespModel
import com.example.mypsychologist.data.model.NewFreeDiaryModel
import com.example.mypsychologist.data.model.NewFreeDiaryWithDateModel
import com.example.mypsychologist.data.model.SaveMoodModel
import com.example.mypsychologist.data.model.SaveMoodWithDateModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FreeDiaryDataSourceImpl @Inject constructor(private val api: FreeDiaryService) :
    FreeDiaryDataSource, BaseDataSource() {

    override suspend fun getFreeDiaries(): Resource<List<FreeDiaryModel>> = getResult {
        withContext(Dispatchers.IO) {
            api.getFreeDiaryList()
        }
    }

    override suspend fun getFreeDiariesByDate(date: String): Flow<Resource<List<FreeDiaryModel>>> =
        flow {
            emit(Resource.Loading)
            emit(getResult {
                api.getFreeDiariesByDay(date)
            })
        }.flowOn(Dispatchers.IO)

    override suspend fun addFreeDiary(freeDiary: NewFreeDiaryModel): Resource<String> = getResult {
        withContext(Dispatchers.IO) {
            api.addFreeDiary(freeDiary = freeDiary)
        }
    }

    override suspend fun saveMoodTracker(saveMoodModel: SaveMoodModel): Resource<MoodTrackerRespModel> =
        getResult {
            api.saveMoodTracker(saveMoodModel)
        }

    override suspend fun saveMoodTrackerWithDate(saveMoodModel: SaveMoodWithDateModel): Flow<Resource<MoodTrackerRespModel>> = flow<Resource<MoodTrackerRespModel>> {
        emit(
            getResult {
                api.saveMoodTrackerWithDate(saveMoodModel)
            }
        )
    }.flowOn(Dispatchers.IO)

    override suspend fun addFreeDiaryWithDate(freeDiaryModel: NewFreeDiaryWithDateModel): Flow<Resource<String>> =
        flow {
            emit(getResult {
                api.addFreeDiaryWithDate(freeDiaryModel)
            })
        }.flowOn(Dispatchers.IO)

    override suspend fun getAllMoodTrackers(date: String): Flow<Resource<List<MoodTrackerPresentModel>>> =
        flow {
            emit(getResult {
                api.getAllMoodTrackers(date)
            })
    }.flowOn(Dispatchers.IO)

    override suspend fun getDatesWithDiaries(month: Int): Flow<Resource<List<CalendarResponseModel>>> = flow<Resource<List<CalendarResponseModel>>> {
        emit(
            getResult {
                api.getDatesWithDiary(month)
            }
        )
    }.flowOn(Dispatchers.IO)
}