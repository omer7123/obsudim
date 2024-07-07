package com.example.mypsychologist.data.remote.exercises

import com.example.mypsychologist.core.BaseDataSource
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.model.CBTDiaryCardModel
import com.example.mypsychologist.data.model.CBTDiaryModel
import javax.inject.Inject

class DiaryDataSourceImpl @Inject constructor(private val api: DiaryService) : DiaryDataSource, BaseDataSource() {
    override suspend fun save(diary: CBTDiaryModel): Resource<String> = getResult {
        api.saveCBTDiary(diary)
    }

    override suspend fun getCBTDiaryRecord(id: String): Resource<CBTDiaryModel> = getResult {
        api.getCBTDiaryRecord(id)
    }

    override suspend fun getCBTDiary(userId: String): Resource<List<CBTDiaryCardModel>> = getResult {
        api.getCBTDiary(userId)
    }
}