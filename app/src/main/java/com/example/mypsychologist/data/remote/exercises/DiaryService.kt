package com.example.mypsychologist.data.remote.exercises

import com.example.mypsychologist.data.model.CBTDiaryCardModel
import com.example.mypsychologist.data.model.CBTDiaryModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface DiaryService {

    @POST("/diary/writing_think_diary")
    suspend fun saveCBTDiary(@Body diary: CBTDiaryModel): Response<String>

    @POST("/diary/reading_think_diary")
    suspend fun getCBTDiaryRecord(@Body think_diary_id: String): Response<CBTDiaryModel>

    @GET("/problem/get_all_think_diary/{user_id}")
    suspend fun getCBTDiary(@Path("user_id") userId: String): Response<List<CBTDiaryCardModel>>
}