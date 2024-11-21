package com.example.mypsychologist.domain.repository.retrofit

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.exerciseEntity.DailyExerciseEntity
import com.example.mypsychologist.domain.entity.exerciseEntity.DailyTaskMarkIdEntity
import com.example.mypsychologist.domain.entity.exerciseEntity.ExerciseDetailEntity
import com.example.mypsychologist.domain.entity.exerciseEntity.ExerciseEntity
import com.example.mypsychologist.domain.entity.exerciseEntity.ExerciseResultRequestEntity
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {
    suspend fun getAllExercises(): Flow<Resource<List<ExerciseEntity>>>
    suspend fun getExerciseDetail(id: String): Flow<Resource<ExerciseDetailEntity>>
    suspend fun getDailyExercises(): Flow<Resource<List<DailyExerciseEntity>>>
    suspend fun markAsCompleteDailyTask(dailyTaskMarkIdEntity: DailyTaskMarkIdEntity): Flow<Resource<String>>
    suspend fun saveExerciseResult(result: ExerciseResultRequestEntity): Flow<Resource<DailyTaskMarkIdEntity>>
}