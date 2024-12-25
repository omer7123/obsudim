package com.example.mypsychologist.data.repository

import android.util.Log
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.converters.toDiaryEntities
import com.example.mypsychologist.data.converters.toEntity
import com.example.mypsychologist.data.converters.toModel
import com.example.mypsychologist.data.local.sharedPref.AuthenticationSharedPrefDataSource
import com.example.mypsychologist.data.remote.exercises.DiaryDataSource
import com.example.mypsychologist.domain.entity.DiaryRecordEntity
import com.example.mypsychologist.domain.entity.ThoughtDiaryEntity
import com.example.mypsychologist.domain.repository.retrofit.CbtRepository
import javax.inject.Inject

class CbtRepositoryImpl @Inject constructor(
    private val diaryDataSource: DiaryDataSource,
    private val localDataSource: AuthenticationSharedPrefDataSource
) : CbtRepository {

    override suspend fun getThoughtDiaries(): Resource<List<DiaryRecordEntity>> =
        when (val result = diaryDataSource.getCBTDiary(localDataSource.getUserId())) {
            is Resource.Error -> {
                Log.d("Problem Error", result.msg.toString())
                Resource.Error(result.msg.toString(), null)
            }

            is Resource.Loading -> Resource.Loading
            is Resource.Success ->
                Resource.Success(result.data.toDiaryEntities())
        }

    override suspend fun getThoughtDiary(id: String): Resource<ThoughtDiaryEntity> =
        when (val result = diaryDataSource.getCBTDiaryRecord(id)) {
            is Resource.Error -> {
                Log.d("Diary Error", result.msg.toString())
                Resource.Error(result.msg.toString(), null)
            }

            is Resource.Loading -> Resource.Loading
            is Resource.Success ->
                Resource.Success(result.data.toEntity())
        }

    override suspend fun saveThoughtDiary(it: ThoughtDiaryEntity): Resource<String> =
        when (val result = diaryDataSource.save(it.toModel())) {
            is Resource.Error -> {
                Log.d("Diary Error", result.msg.toString())
                Resource.Error(result.msg.toString(), null)
            }

            is Resource.Loading -> Resource.Loading
            is Resource.Success ->
                Resource.Success(result.data.id)
        }
}