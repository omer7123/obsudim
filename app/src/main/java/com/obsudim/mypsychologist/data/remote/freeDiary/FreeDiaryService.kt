package com.obsudim.mypsychologist.data.remote.freeDiary

import com.obsudim.mypsychologist.data.model.CalendarResponseModel
import com.obsudim.mypsychologist.data.model.EmojiModel
import com.obsudim.mypsychologist.data.model.FreeDiaryModel
import com.obsudim.mypsychologist.data.model.MoodTrackerPresentModel
import com.obsudim.mypsychologist.data.model.MoodTrackerRespModel
import com.obsudim.mypsychologist.data.model.NewFreeDiaryWithDateModel
import com.obsudim.mypsychologist.data.model.SaveMoodModel
import com.obsudim.mypsychologist.data.model.SaveMoodWithDateModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface FreeDiaryService {

    @GET("/diary/reading_free_diary")
    suspend fun getFreeDiaryList(): Response<List<FreeDiaryModel>>

    @POST("/diary")
    suspend fun addFreeDiary(@Body freeDiary: NewFreeDiaryWithDateModel): Response<Unit>

    @GET("/diary")
    suspend fun getFreeDiariesByDay(@Query("day") date: String): Response<List<FreeDiaryModel>>

    @POST("/mood_tracker")
    suspend fun saveMoodTracker(@Body saveMoodTracker: SaveMoodModel): Response<MoodTrackerRespModel>

    @POST("/mood_tracker")
    suspend fun saveMoodTrackerWithDate(@Body saveMoodTracker: SaveMoodWithDateModel): Response<MoodTrackerRespModel>

    @GET("/mood_tracker")
    suspend fun getAllMoodTrackers(@Query("day") day: String): Response<List<MoodTrackerPresentModel>>

    @GET("/diary/by_month")
    suspend fun getDatesWithDiary(@Query("timestamp") date: Int): Response<List<CalendarResponseModel>>

    @GET("/mood_tracker/emoji")
    suspend fun getAllEmojies(): Response<List<EmojiModel>>
}