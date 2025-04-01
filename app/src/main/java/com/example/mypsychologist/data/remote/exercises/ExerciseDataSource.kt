package com.example.mypsychologist.data.remote.exercises

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.model.CBTDiaryModel
import com.example.mypsychologist.data.model.DailyExerciseModel
import com.example.mypsychologist.data.model.DailyTaskMarkIdModel
import com.example.mypsychologist.data.model.ExerciseDetailModel
import com.example.mypsychologist.data.model.ExerciseDetailResultModel
import com.example.mypsychologist.data.model.ExerciseResultFromAPIModel
import com.example.mypsychologist.data.model.ExerciseResultRequestModel
import com.example.mypsychologist.data.model.ExercisesModel
import com.example.mypsychologist.data.model.ExercisesStatusModel
import com.example.mypsychologist.data.model.SaveExerciseResultResponseModel
import com.example.mypsychologist.data.model.StatusPostResponse
import kotlinx.coroutines.flow.Flow

interface ExerciseDataSource {
    suspend fun getAllExercises(): Flow<Resource<List<ExercisesModel>>>
    suspend fun getExerciseDetail(id: String): Flow<Resource<ExerciseDetailModel>>
    suspend fun saveExerciseResult(data: ExerciseResultRequestModel): Flow<Resource<SaveExerciseResultResponseModel>>
    suspend fun getDailyExercises(): Flow<Resource<List<DailyExerciseModel>>>
    suspend fun markAsCompleteTask(taskCompleted: DailyTaskMarkIdModel): Flow<Resource<StatusPostResponse>>
    suspend fun getExerciseResults(exerciseId: String): Flow<Resource<List<ExerciseResultFromAPIModel>>>
    suspend fun getExerciseDetailResult(id: String): Flow<Resource<ExerciseDetailResultModel>>
    suspend fun getAllStatusExercises(): Flow<Resource<List<ExercisesStatusModel>>>
    suspend fun saveCBTDiary(diary: CBTDiaryModel): Flow<Resource<SaveExerciseResultResponseModel>>
}