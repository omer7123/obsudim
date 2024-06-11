package com.example.mypsychologist.data.remote.freeDiary

import com.example.mypsychologist.domain.entity.FreeDiary
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface FreeDiaryService {

    @GET("/diary/reading_free_diary")
    suspend fun getFreeDiaryList(): Response<List<String>>

    @POST("/diary/writing_free_diary")
    suspend fun addFreeDiary(@Body freeDiary: FreeDiary): Response<String>
}