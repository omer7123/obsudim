package com.example.mypsychologist.domain.repository.retrofit

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.FreeDiary

interface FreeDiaryRepository {

    suspend fun getFreeDiaryList(): Resource<List<FreeDiary>>
    suspend fun addFreeDiary(freeDiary: FreeDiary): Resource<String>
}