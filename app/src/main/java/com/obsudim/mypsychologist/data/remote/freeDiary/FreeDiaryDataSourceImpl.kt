package com.obsudim.mypsychologist.data.remote.freeDiary

import com.obsudim.mypsychologist.core.BaseDataSource
import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.data.model.CalendarResponseModel
import com.obsudim.mypsychologist.data.model.EmojiModel
import com.obsudim.mypsychologist.data.model.FreeDiaryModel
import com.obsudim.mypsychologist.data.model.MoodTrackerPresentModel
import com.obsudim.mypsychologist.data.model.MoodTrackerRespModel
import com.obsudim.mypsychologist.data.model.NewFreeDiaryWithDateModel
import com.obsudim.mypsychologist.data.model.SaveMoodModel
import com.obsudim.mypsychologist.data.model.SaveMoodWithDateModel
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

    override suspend fun getFreeDiariesByDate(date: String):
            Flow<Resource<List<FreeDiaryModel>>> =
        flow {
            emit(Resource.Loading)
            emit(getResult {
                api.getFreeDiariesByDay(date)
            })
        }.flowOn(Dispatchers.IO)

    override suspend fun addFreeDiary(freeDiary: NewFreeDiaryWithDateModel): Resource<Unit> = getResult {
        withContext(Dispatchers.IO) {
            api.addFreeDiary(freeDiary = freeDiary)
        }
    }

    override suspend fun saveMoodTracker(saveMoodModel: SaveMoodModel): Flow<Resource<MoodTrackerRespModel>> =

        flow {
            emit(Resource.Loading)
            emit(getResult {
                    api.saveMoodTracker(saveMoodModel)
                }
            )
        }.flowOn(Dispatchers.IO)

    override suspend fun saveMoodTrackerWithDate(saveMoodModel: SaveMoodWithDateModel): Flow<Resource<MoodTrackerRespModel>> =
        flow {
            emit(Resource.Loading)
        emit(
            getResult {
                api.saveMoodTrackerWithDate(saveMoodModel)
            }
        )
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

    override suspend fun getAllEmojies(): Flow<Resource<List<EmojiModel>>> = flow {
        emit(Resource.Loading)
        emit(
            getResult {
                api.getAllEmojies()
            }
        )
    }.flowOn(Dispatchers.IO)
}