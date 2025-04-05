package com.example.mypsychologist.data.remote.exercises

import com.example.mypsychologist.data.model.CBTDiaryModel
import com.example.mypsychologist.data.model.exerciseModels.DailyExerciseModel
import com.example.mypsychologist.data.model.exerciseModels.DailyTaskMarkIdModel
import com.example.mypsychologist.data.model.exerciseModels.DefinitionProblemGroupExerciseModel
import com.example.mypsychologist.data.model.exerciseModels.DefinitionProblemGroupHistoryModel
import com.example.mypsychologist.data.model.exerciseModels.ExerciseDetailModel
import com.example.mypsychologist.data.model.exerciseModels.ExerciseDetailResultModel
import com.example.mypsychologist.data.model.exerciseModels.ExerciseResultFromAPIModel
import com.example.mypsychologist.data.model.exerciseModels.ExerciseResultRequestModel
import com.example.mypsychologist.data.model.exerciseModels.ExerciseSaveResponseModel
import com.example.mypsychologist.data.model.exerciseModels.ExercisesModel
import com.example.mypsychologist.data.model.exerciseModels.ExercisesStatusModel
import com.example.mypsychologist.data.model.exerciseModels.SaveExerciseResultResponseModel
import com.example.mypsychologist.data.model.exerciseModels.StatusPostResponse
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

    @PATCH("/daily_tasks")
    suspend fun markAsComplete(@Body body: DailyTaskMarkIdModel): Response<StatusPostResponse>

    @GET("/exercise/get_exercise_results/{exercise_id}")
    suspend fun getExerciseResults(@Path("exercise_id") exerciseId: String): Response<List<ExerciseResultFromAPIModel>>

    @GET("/exercise/get_exercise_result/{completed_exercise_id}")
    suspend fun getDetailExerciseResult(@Path("completed_exercise_id") id: String): Response<ExerciseDetailResultModel>

    @POST("/defining_problem_groups/save_exercise_result")
    suspend fun saveDefinitionProblemGroupResult(@Body data: DefinitionProblemGroupExerciseModel): Response<ExerciseSaveResponseModel>
    @POST("/diary/writing_think_diary")
    suspend fun saveCBTDiary(@Body diary: CBTDiaryModel): Response<SaveExerciseResultResponseModel>

    @GET("/defining_problem_groups/get_all_by_user")
    suspend fun getAllDPGResults(): Response<List<DefinitionProblemGroupHistoryModel>>
}