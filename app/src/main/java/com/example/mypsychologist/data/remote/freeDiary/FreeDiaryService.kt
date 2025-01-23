package com.example.mypsychologist.data.remote.freeDiary

import com.example.mypsychologist.data.model.FreeDiaryModel
import com.example.mypsychologist.data.model.MoodTrackerRespModel
import com.example.mypsychologist.data.model.NewFreeDiaryModel
import com.example.mypsychologist.data.model.NewFreeDiaryWithDateModel
import com.example.mypsychologist.data.model.SaveMoodModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface FreeDiaryService {

    @GET("/diary/reading_free_diary")
    suspend fun getFreeDiaryList(): Response<List<FreeDiaryModel>>

    @POST("/diary/writing_free_diary")
    suspend fun addFreeDiary(@Body freeDiary: NewFreeDiaryModel): Response<String>

    @POST("/diary/writing_free_diary_with_date")
    suspend fun addFreeDiaryWithDate(@Body data: NewFreeDiaryWithDateModel): Response<String>

    @GET("/diary/reading_free_diary_with_date")
    suspend fun getFreeDiariesByDay(@Query("date") date: String): Response<List<FreeDiaryModel>>

    @POST("/mood_tracker/save_mood_tracker")
    suspend fun saveMoodTracker(@Body saveMoodTracker: SaveMoodModel): Response<MoodTrackerRespModel>
}