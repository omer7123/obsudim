package com.obsudim.mypsychologist.data.remote.exercises

import com.obsudim.mypsychologist.data.model.CBTDiaryModel
import com.obsudim.mypsychologist.data.model.exerciseModels.DailyExerciseModel
import com.obsudim.mypsychologist.data.model.exerciseModels.DailyTaskMarkIdModel
import com.obsudim.mypsychologist.data.model.exerciseModels.ExerciseDetailModel
import com.obsudim.mypsychologist.data.model.exerciseModels.ExerciseDetailResultModel
import com.obsudim.mypsychologist.data.model.exerciseModels.ExerciseInfoPreview
import com.obsudim.mypsychologist.data.model.exerciseModels.ExerciseMock
import com.obsudim.mypsychologist.data.model.exerciseModels.ExerciseResultRequestModel
import com.obsudim.mypsychologist.data.model.exerciseModels.ExercisesStatusModel
import com.obsudim.mypsychologist.data.model.exerciseModels.ResultsExercise
import com.obsudim.mypsychologist.data.model.exerciseModels.SaveExerciseResultResponseModel
import com.obsudim.mypsychologist.data.model.exerciseModels.StatusPostResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ExerciseService {

    @GET("/exercises/{exercise_id}")
    suspend fun getExerciseInfoPreview(
        @Path("exercise_id") id: String
    ): Response<ExerciseInfoPreview>

    @GET("/exercises/")
    suspend fun getAllExercises(): Response<ExerciseMock>

    @GET("/exercise/all")
    suspend fun getAllExerciseStatus(): Response<List<ExercisesStatusModel>>

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

    @PATCH("/daily_tasks/complete")
    suspend fun markAsComplete(@Body body: DailyTaskMarkIdModel): Response<StatusPostResponse>

    @GET("/exercises/{exercise_id}/results")
    suspend fun getExerciseResults(@Path("exercise_id") exerciseId: String): Response<ResultsExercise>

    @GET("/exercise/get_exercise_result/{completed_exercise_id}")
    suspend fun getDetailExerciseResult(@Path("completed_exercise_id") id: String): Response<ExerciseDetailResultModel>

    @POST("/diary/writing_think_diary")
    suspend fun saveCBTDiary(@Body diary: CBTDiaryModel): Response<SaveExerciseResultResponseModel>

}