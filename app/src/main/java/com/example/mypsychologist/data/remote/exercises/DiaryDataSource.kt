package com.example.mypsychologist.data.remote.exercises

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.model.CBTDiaryCardModel
import com.example.mypsychologist.data.model.CBTDiaryModel

interface DiaryDataSource {
    suspend fun save(diary: CBTDiaryModel): Resource<String>
    suspend fun getCBTDiaryRecord(id: String): Resource<CBTDiaryModel>
    suspend fun getCBTDiary(userId: String): Resource<List<CBTDiaryCardModel>>
}