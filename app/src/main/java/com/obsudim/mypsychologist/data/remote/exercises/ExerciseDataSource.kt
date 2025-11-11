package com.obsudim.mypsychologist.data.remote.exercises

import com.obsudim.mypsychologist.core.Resource
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
import kotlinx.coroutines.flow.Flow

interface ExerciseDataSource {
    suspend fun getExerciseInfoPreview(id: String): Flow<Resource<ExerciseInfoPreview>>
    suspend fun getAllExercises(): Flow<Resource<ExerciseMock>>
    suspend fun getExerciseDetail(id: String): Flow<Resource<ExerciseDetailModel>>
    suspend fun saveExerciseResult(data: ExerciseResultRequestModel): Flow<Resource<SaveExerciseResultResponseModel>>
    suspend fun getDailyExercises(): Flow<Resource<List<DailyExerciseModel>>>
    suspend fun markAsCompleteTask(taskCompleted: DailyTaskMarkIdModel): Flow<Resource<StatusPostResponse>>
    suspend fun getExerciseResults(exerciseId: String): Flow<Resource<ResultsExercise>>
    suspend fun getExerciseDetailResult(id: String): Flow<Resource<ExerciseDetailResultModel>>
    suspend fun getAllStatusExercises(): Flow<Resource<List<ExercisesStatusModel>>>
    suspend fun saveCBTDiary(diary: CBTDiaryModel): Flow<Resource<SaveExerciseResultResponseModel>>
}