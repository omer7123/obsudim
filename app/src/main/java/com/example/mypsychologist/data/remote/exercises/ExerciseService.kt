package com.example.mypsychologist.data.remote.exercises

import com.example.mypsychologist.data.model.DailyExerciseModel
import com.example.mypsychologist.data.model.DailyTaskMarkIdModel
import com.example.mypsychologist.data.model.ExerciseDetailModel
import com.example.mypsychologist.data.model.ExercisesModel
import com.example.mypsychologist.data.model.StatusPostResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

interface ExerciseService {

    @GET("/exercise/get_all_exercises")
    suspend fun getAllExercises(): Response<List<ExercisesModel>>

    @GET("/exercise/get_exercise")
    suspend fun getExerciseDetail(
        @Query("exercise_id") id: String
    ): Response<ExerciseDetailModel>

    @POST("/exercise/save_exercise_result")
    suspend fun saveExerciseResult(
        @Body resultExercise:String
    ): Response<String>

    @GET("/daily_tasks")
    suspend fun getDailyExercises(): Response<List<DailyExerciseModel>>

    @PATCH("/daily_tasks")
    suspend fun markAsComplete(@Body body: DailyTaskMarkIdModel): Response<StatusPostResponse>
}