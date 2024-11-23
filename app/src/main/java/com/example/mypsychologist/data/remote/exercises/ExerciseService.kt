package com.example.mypsychologist.data.remote.exercises

import com.example.mypsychologist.data.model.DailyExerciseModel
import com.example.mypsychologist.data.model.DailyTaskMarkIdModel
import com.example.mypsychologist.data.model.ExerciseDetailModel
import com.example.mypsychologist.data.model.ExerciseDetailResultModel
import com.example.mypsychologist.data.model.ExerciseResultFromAPIModel
import com.example.mypsychologist.data.model.ExerciseResultRequestModel
import com.example.mypsychologist.data.model.ExercisesModel
import com.example.mypsychologist.data.model.SaveExerciseResultResponseModel
import com.example.mypsychologist.data.model.StatusPostResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
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
        @Body resultExercise: ExerciseResultRequestModel
    ): Response<SaveExerciseResultResponseModel>

    @GET("/daily_tasks")
    suspend fun getDailyExercises(): Response<List<DailyExerciseModel>>

    @PATCH("/daily_tasks")
    suspend fun markAsComplete(@Body body: DailyTaskMarkIdModel): Response<StatusPostResponse>

    @GET("/exercise/get_exercise_results/{exercise_id}")
    suspend fun getExerciseResults(@Path("exercise_id") exerciseId: String): Response<List<ExerciseResultFromAPIModel>>

    @GET("/exercise/get_exercise_result/{completed_exercise_id}")
    suspend fun getDetailExerciseResult(@Path("completed_exercise_id") id: String): Response<ExerciseDetailResultModel>

}