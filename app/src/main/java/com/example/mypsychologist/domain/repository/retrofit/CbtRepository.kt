package com.example.mypsychologist.domain.repository.retrofit

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.DiaryRecordEntity
import com.example.mypsychologist.domain.entity.ThoughtDiaryEntity

interface CbtRepository {
    suspend fun getThoughtDiaries(): Resource<List<DiaryRecordEntity>>
    suspend fun getThoughtDiary(id: String): Resource<ThoughtDiaryEntity>
    suspend fun saveThoughtDiary(it: ThoughtDiaryEntity): Resource<String>
}
