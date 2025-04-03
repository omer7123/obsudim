package com.example.mypsychologist.domain.repository.retrofit

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.ThoughtDiaryEntity
import com.example.mypsychologist.domain.entity.exerciseEntity.DailyExerciseEntity
import com.example.mypsychologist.domain.entity.exerciseEntity.DailyTaskMarkIdEntity
import com.example.mypsychologist.domain.entity.exerciseEntity.DefinitionProblemGroupExerciseEntity
import com.example.mypsychologist.domain.entity.exerciseEntity.ExerciseDetailEntity
import com.example.mypsychologist.domain.entity.exerciseEntity.ExerciseDetailResultEntity
import com.example.mypsychologist.domain.entity.exerciseEntity.ExerciseEntity
import com.example.mypsychologist.domain.entity.exerciseEntity.ExerciseResultFromAPIEntity
import com.example.mypsychologist.domain.entity.exerciseEntity.ExerciseResultRequestEntity
import com.example.mypsychologist.domain.entity.exerciseEntity.ExercisesStatusEntity
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {
    suspend fun getAllExercises(): Flow<Resource<List<ExerciseEntity>>>
    suspend fun getExerciseDetail(id: String): Flow<Resource<ExerciseDetailEntity>>
    suspend fun getDailyExercises(): Flow<Resource<List<DailyExerciseEntity>>>
    suspend fun markAsCompleteDailyTask(dailyTaskMarkIdEntity: DailyTaskMarkIdEntity): Flow<Resource<String>>
    suspend fun saveExerciseResult(result: ExerciseResultRequestEntity): Flow<Resource<DailyTaskMarkIdEntity>>
    suspend fun getExerciseResults(exerciseId: String): Flow<Resource<List<ExerciseResultFromAPIEntity>>>
    suspend fun getExerciseDetailResult(id: String): Flow<Resource<ExerciseDetailResultEntity>>
    suspend fun getAllStatusExercise(): Flow<Resource<List<ExercisesStatusEntity>>>
    suspend fun saveDefinitionProblemGroupResult(result: DefinitionProblemGroupExerciseEntity): Flow<Resource<Boolean>>

    suspend fun saveCBTDiary(diary: ThoughtDiaryEntity): Flow<Resource<DailyTaskMarkIdEntity>>
}