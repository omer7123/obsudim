package com.example.mypsychologist.domain.repository.retrofit

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.diaryEntity.FreeDiaryEntity
import com.example.mypsychologist.domain.entity.diaryEntity.NewFreeDiaryEntity

interface FreeDiaryRepository {
    suspend fun getFreeDiaries(): Resource<List<FreeDiaryEntity>>
    suspend fun addFreeDiary(freeDiary: NewFreeDiaryEntity): Resource<String>
}