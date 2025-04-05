package com.example.mypsychologist.data.remote.exercises

import com.example.mypsychologist.core.BaseDataSource
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ExerciseDataSourceImpl @Inject constructor(private val api: ExerciseService) :
    BaseDataSource(),
    ExerciseDataSource {

    override suspend fun getAllExercises(): Flow<Resource<List<ExercisesModel>>> = flow {
        emit(Resource.Loading)
        emit(getResult {
            api.getAllExercises()
        })
    }.flowOn(Dispatchers.IO)

    override suspend fun getExerciseDetail(id: String): Flow<Resource<ExerciseDetailModel>> = flow{
        emit(Resource.Loading)
        emit(
            getResult {
                api.getExerciseDetail(id)
            }
        )
    }.flowOn(Dispatchers.IO)

    override suspend fun saveExerciseResult(data: ExerciseResultRequestModel): Flow<Resource<SaveExerciseResultResponseModel>> = flow<Resource<SaveExerciseResultResponseModel>> {
        emit(Resource.Loading)
        emit(
            getResult {
                api.saveExerciseResult(data)
            }
        )
    }.flowOn(Dispatchers.IO)

    override suspend fun getDailyExercises(): Flow<Resource<List<DailyExerciseModel>>> = flow{
        emit(Resource.Loading)
        emit(
            getResult {
                api.getDailyExercises()
            }
        )
    }.flowOn(Dispatchers.IO)

    override suspend fun markAsCompleteTask(taskCompleted: DailyTaskMarkIdModel): Flow<Resource<StatusPostResponse>> = flow {
        emit(Resource.Loading)
        emit(
                getResult {
                    api.markAsComplete(taskCompleted)
                }
            )

        }.flowOn(Dispatchers.IO)

    override suspend fun getExerciseResults(exerciseId: String): Flow<Resource<List<ExerciseResultFromAPIModel>>> = flow{
        emit(Resource.Loading)
        emit(
            getResult {
                api.getExerciseResults(exerciseId)
            }
        )
    }.flowOn(Dispatchers.IO)

    override suspend fun getExerciseDetailResult(id: String): Flow<Resource<ExerciseDetailResultModel>> = flow{
        emit(Resource.Loading)
        emit(
            getResult {
                api.getDetailExerciseResult(id)
            }
        )
    }.flowOn(Dispatchers.IO)

    override suspend fun getAllStatusExercises(): Flow<Resource<List<ExercisesStatusModel>>> = flow{
        emit(Resource.Loading)
        emit(
            getResult {
                api.getAllExerciseStatus()
            }
        )
    }.flowOn(Dispatchers.IO)

    override suspend fun saveDefinitionProblemGroupResult(definitionDataModel: DefinitionProblemGroupExerciseModel): Flow<Resource<ExerciseSaveResponseModel>> = flow{
        emit(Resource.Loading)
        emit(
            getResult {
                api.saveDefinitionProblemGroupResult(definitionDataModel)
            }
        )
    }.flowOn(Dispatchers.IO)

    override suspend fun saveCBTDiary(diary: CBTDiaryModel): Flow<Resource<SaveExerciseResultResponseModel>> = flow {
        emit(Resource.Loading)
        emit(
            getResult {
                api.saveCBTDiary(diary)
            }
        )
    }.flowOn(Dispatchers.IO)

    override suspend fun getExerciseDPGResults(): Flow<Resource<List<DefinitionProblemGroupHistoryModel>>> = flow{
        emit(Resource.Loading)
        emit(
            getResult {
                api.getAllDPGResults()
            }
        )
    }.flowOn(Dispatchers.IO)
}