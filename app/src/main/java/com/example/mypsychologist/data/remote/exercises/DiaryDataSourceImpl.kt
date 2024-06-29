package com.example.mypsychologist.data.remote.exercises

import android.util.Log
import com.example.mypsychologist.core.BaseDataSource
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.model.CBTDiaryModel
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import javax.inject.Inject

class DiaryDataSourceImpl @Inject constructor(private val api: DiaryService) : DiaryDataSource, BaseDataSource() {
    override suspend fun save(diary: CBTDiaryModel): Resource<String> = getResult {
        val result = api.saveCBTDiary(diary)
        val moshi: Moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
        val jsonAdapter: JsonAdapter<CBTDiaryModel> = moshi.adapter(CBTDiaryModel::class.java)

        val json: String = jsonAdapter.toJson(diary)
        Log.d("AAAAAAA", json)
        result
    }

    override suspend fun getCBTDiaryRecord(id: String): Resource<CBTDiaryModel> = getResult {
        api.getCBTDiaryRecord(id)
    }
}