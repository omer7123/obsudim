package com.example.mypsychologist.data.remote.exercises

import com.example.mypsychologist.core.Resource
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
    suspend fun saveDefinitionProblemGroupResult(toModel: DefinitionProblemGroupExerciseModel): Flow<Resource<ExerciseSaveResponseModel>>
    suspend fun saveCBTDiary(diary: CBTDiaryModel): Flow<Resource<SaveExerciseResultResponseModel>>
    suspend fun getExerciseDPGResults(): Flow<Resource<List<DefinitionProblemGroupHistoryModel>>>
}