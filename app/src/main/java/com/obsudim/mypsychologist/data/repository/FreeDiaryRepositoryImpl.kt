package com.obsudim.mypsychologist.data.repository

import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.data.converters.toEntity
import com.obsudim.mypsychologist.data.converters.toFreeDiaryEntity
import com.obsudim.mypsychologist.data.converters.toModel
import com.obsudim.mypsychologist.data.converters.toNewFreeDiaryModel
import com.obsudim.mypsychologist.data.remote.freeDiary.FreeDiaryDataSource
import com.obsudim.mypsychologist.domain.entity.diaryEntity.CalendarResponseEntity
import com.obsudim.mypsychologist.domain.entity.diaryEntity.EmojiEntity
import com.obsudim.mypsychologist.domain.entity.diaryEntity.FreeDiaryEntity
import com.obsudim.mypsychologist.domain.entity.diaryEntity.MoodTrackerRespEntity
import com.obsudim.mypsychologist.domain.entity.diaryEntity.MoodTrackerResultEntity
import com.obsudim.mypsychologist.domain.entity.diaryEntity.NewFreeDiaryWithDateEntity
import com.obsudim.mypsychologist.domain.entity.diaryEntity.SaveMoodEntity
import com.obsudim.mypsychologist.domain.entity.diaryEntity.SaveMoodWithDateEntity
import com.obsudim.mypsychologist.domain.repository.retrofit.FreeDiaryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FreeDiaryRepositoryImpl @Inject constructor(private val dataSource: FreeDiaryDataSource) :
    FreeDiaryRepository {

    override suspend fun getFreeDiaries(): Resource<List<FreeDiaryEntity>> {

        return when (val listModel = dataSource.getFreeDiaries()) {
            is Resource.Error -> Resource.Error(listModel.msg, null)
            Resource.Loading -> Resource.Loading
            is Resource.Success -> {
                val listEntity = listModel.data.map { it.toFreeDiaryEntity() }
                Resource.Success(listEntity)
            }
        }
    }

    override suspend fun addFreeDiary(freeDiary: NewFreeDiaryWithDateEntity): Resource<Unit> {
        return dataSource.addFreeDiary(freeDiary.toNewFreeDiaryModel())
    }

    override suspend fun getFreeDiariesByDay(day: String):
            Flow<Resource<List<FreeDiaryEntity>>> {
        return dataSource.getFreeDiariesByDate(day).checkResource {freeDiaries->
            freeDiaries.map {
                it.toFreeDiaryEntity()
            }
        }
    }

    override suspend fun saveMoodTracker(saveMoodEntity: SaveMoodEntity): Flow<Resource<MoodTrackerRespEntity>> {
        return dataSource.saveMoodTracker(saveMoodEntity.toModel()).checkResource { respModel ->
            respModel.toEntity()
        }

    }

    override suspend fun saveMoodTrackerWithDate(moodTrack: SaveMoodWithDateEntity): Flow<Resource<MoodTrackerRespEntity>>{
        return dataSource.saveMoodTrackerWithDate(moodTrack.toModel()).checkResource {resp->
            resp.toEntity()
        }
    }

    override suspend fun getAllMoodTrackers(date: String): Flow<Resource<List<MoodTrackerResultEntity>>> {
        return dataSource.getAllMoodTrackers(date).checkResource{ listOfTrack->
            listOfTrack.map {
                it.toEntity()
            }
        }
    }

    override suspend fun getDatesWithDiaries(month: Int): Flow<Resource<List<CalendarResponseEntity>>> {
        return dataSource.getDatesWithDiaries(month).checkResource {listOfDates->
            listOfDates.map {
                it.toEntity()
            }
        }
    }

    override suspend fun getAllEmojies(): Flow<Resource<List<EmojiEntity>>> {
        return dataSource.getAllEmojies().checkResource { listEmojies->
            listEmojies.map {
                it.toEntity()
            }
        }
    }
}


