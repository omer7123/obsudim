package com.example.mypsychologist.data.remote.freeDiary

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.FreeDiary

interface FreeDiaryDataSource {
    suspend fun getFreeDiaryList(): Resource<List<String>>
    suspend fun addFreeDiary(freeDiary: FreeDiary): Resource<String>
}