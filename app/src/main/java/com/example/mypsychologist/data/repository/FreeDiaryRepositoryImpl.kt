package com.example.mypsychologist.data.repository

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.converters.toEntity
import com.example.mypsychologist.data.converters.toFreeDiaryEntity
import com.example.mypsychologist.data.converters.toModel
import com.example.mypsychologist.data.converters.toNewFreeDiaryModel
import com.example.mypsychologist.data.remote.freeDiary.FreeDiaryDataSource
import com.example.mypsychologist.domain.entity.diaryEntity.FreeDiaryEntity
import com.example.mypsychologist.domain.entity.diaryEntity.MoodTrackerRespEntity
import com.example.mypsychologist.domain.entity.diaryEntity.NewFreeDiaryEntity
import com.example.mypsychologist.domain.entity.diaryEntity.SaveMoodEntity
import com.example.mypsychologist.domain.repository.retrofit.FreeDiaryRepository
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

    override suspend fun addFreeDiary(freeDiary: NewFreeDiaryEntity): Resource<String> {
        return dataSource.addFreeDiary(freeDiary.toNewFreeDiaryModel())
    }

    override suspend fun saveMoodTracker(saveMoodEntity: SaveMoodEntity): Resource<MoodTrackerRespEntity> {
        return when (val res = dataSource.saveMoodTracker(saveMoodEntity.toModel())) {
            is Resource.Success -> Resource.Success(res.data.toEntity())
            is Resource.Error -> Resource.Error(res.msg.toString(), null)
            Resource.Loading -> Resource.Loading
        }
    }
}