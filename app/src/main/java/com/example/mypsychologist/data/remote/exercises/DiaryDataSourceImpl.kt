package com.example.mypsychologist.data.remote.exercises

import android.util.Log
import com.example.mypsychologist.core.BaseDataSource
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.model.CBTDiaryCardModel
import com.example.mypsychologist.data.model.CBTDiaryModel
import com.example.mypsychologist.data.model.DiaryIdModel
import javax.inject.Inject

class DiaryDataSourceImpl @Inject constructor(private val api: DiaryService) : DiaryDataSource, BaseDataSource() {
    override suspend fun save(diary: CBTDiaryModel): Resource<String> = getResult {
        api.saveCBTDiary(diary)
    }

    override suspend fun getCBTDiaryRecord(id: String): Resource<CBTDiaryModel> = getResult {
        api.getCBTDiaryRecord(DiaryIdModel( id))
    }

    override suspend fun getCBTDiary(userId: String): Resource<List<CBTDiaryCardModel>> = getResult {
        api.getCBTDiary(userId)
    }
}