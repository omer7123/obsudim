package com.example.mypsychologist.data.remote.freeDiary

import com.example.mypsychologist.data.model.FreeDiaryModel
import com.example.mypsychologist.data.model.NewFreeDiaryModel
import com.example.mypsychologist.domain.entity.diaryEntity.NewFreeDiaryEntity
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface FreeDiaryService {

    @GET("/diary/reading_free_diary")
    suspend fun getFreeDiaryList(): Response<List<FreeDiaryModel>>

    @POST("/diary/writing_free_diary")
    suspend fun addFreeDiary(@Body freeDiary: NewFreeDiaryModel): Response<String>
}