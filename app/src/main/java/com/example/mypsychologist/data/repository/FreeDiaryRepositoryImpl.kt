package com.example.mypsychologist.data.repository

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.converters.toFreeDiaryEntity
import com.example.mypsychologist.data.remote.freeDiary.FreeDiaryDataSource
import com.example.mypsychologist.domain.entity.FreeDiary
import com.example.mypsychologist.domain.repository.retrofit.FreeDiaryRepository
import javax.inject.Inject

class FreeDiaryRepositoryImpl @Inject constructor(private val dataSource: FreeDiaryDataSource): FreeDiaryRepository {
    override suspend fun getFreeDiaryList(): Resource<List<FreeDiary>> {
        return when (val result = dataSource.getFreeDiaryList()) {
            is Resource.Error -> Resource.Error(result.msg.toString(), null)
            Resource.Loading -> Resource.Loading
            is Resource.Success -> {
                val listFreeDiaryEntity = result.data.map { it.toFreeDiaryEntity() }
                return Resource.Success(listFreeDiaryEntity)
            }
        }
    }

    override suspend fun addFreeDiary(freeDiary: FreeDiary): Resource<String> {
        return dataSource.addFreeDiary(freeDiary)
    }
}