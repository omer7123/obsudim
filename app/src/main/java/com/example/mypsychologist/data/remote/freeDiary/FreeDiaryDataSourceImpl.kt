package com.example.mypsychologist.data.remote.freeDiary

import com.example.mypsychologist.core.BaseDataSource
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.FreeDiary
import javax.inject.Inject

class FreeDiaryDataSourceImpl @Inject constructor(private val api: FreeDiaryService) :
    FreeDiaryDataSource, BaseDataSource() {
    override suspend fun getFreeDiaryList(): Resource<List<String>> = getResult {
        api.getFreeDiaryList()
    }

    override suspend fun addFreeDiary(freeDiary: FreeDiary): Resource<String> = getResult {
        api.addFreeDiary(freeDiary = freeDiary)
    }
}