package com.example.mypsychologist.data.remote.exercises

import com.example.mypsychologist.data.model.CBTDiaryModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface DiaryService {

    @POST("/diary/writing_think_diary")
    suspend fun saveCBTDiary(@Body diary: CBTDiaryModel): Response<String>

    @POST("/diary/reading_think_diary")
    suspend fun getCBTDiaryRecord(@Body think_diary_id: String): Response<CBTDiaryModel>
}