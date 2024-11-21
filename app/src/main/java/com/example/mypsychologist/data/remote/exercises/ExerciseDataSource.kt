package com.example.mypsychologist.data.remote.exercises

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.model.DailyExerciseModel
import com.example.mypsychologist.data.model.DailyTaskMarkIdModel
import com.example.mypsychologist.data.model.ExerciseDetailModel
import com.example.mypsychologist.data.model.ExercisesModel
import com.example.mypsychologist.data.model.StatusPostResponse
import kotlinx.coroutines.flow.Flow

interface ExerciseDataSource {
    suspend fun getAllExercises(): Flow<Resource<List<ExercisesModel>>>
    suspend fun getExerciseDetail(id: String): Flow<Resource<ExerciseDetailModel>>
    suspend fun getDailyExercises(): Flow<Resource<List<DailyExerciseModel>>>
    suspend fun markAsCompleteTask(taskCompleted: DailyTaskMarkIdModel): Flow<Resource<StatusPostResponse>>
}