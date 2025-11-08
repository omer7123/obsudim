package com.obsudim.mypsychologist.data.remote.exercises

import com.obsudim.mypsychologist.core.BaseDataSource
import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.data.model.CBTDiaryModel
import com.obsudim.mypsychologist.data.model.exerciseModels.DailyExerciseModel
import com.obsudim.mypsychologist.data.model.exerciseModels.DailyTaskMarkIdModel
import com.obsudim.mypsychologist.data.model.exerciseModels.DefinitionProblemGroupExerciseModel
import com.obsudim.mypsychologist.data.model.exerciseModels.DefinitionProblemGroupHistoryModel
import com.obsudim.mypsychologist.data.model.exerciseModels.ExerciseDetailModel
import com.obsudim.mypsychologist.data.model.exerciseModels.ExerciseDetailResultModel
import com.obsudim.mypsychologist.data.model.exerciseModels.ExerciseInfoPreview
import com.obsudim.mypsychologist.data.model.exerciseModels.ExerciseMock
import com.obsudim.mypsychologist.data.model.exerciseModels.ExerciseResultRequestModel
import com.obsudim.mypsychologist.data.model.exerciseModels.ExerciseSaveResponseModel
import com.obsudim.mypsychologist.data.model.exerciseModels.ExercisesStatusModel
import com.obsudim.mypsychologist.data.model.exerciseModels.ResultsExercise
import com.obsudim.mypsychologist.data.model.exerciseModels.SaveExerciseResultResponseModel
import com.obsudim.mypsychologist.data.model.exerciseModels.StatusPostResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ExerciseDataSourceImpl @Inject constructor(private val api: ExerciseService) :
    BaseDataSource(),
    ExerciseDataSource {

    override suspend fun getExerciseInfoPreview(id: String): Flow<Resource<ExerciseInfoPreview>> = flow {
        emit(Resource.Loading)
        emit(getResult {
            api.getExerciseInfoPreview(id)
        })
    }.flowOn(Dispatchers.IO)

    override suspend fun getAllExercises(): Flow<Resource<ExerciseMock>> = flow {
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

    override suspend fun getExerciseResults(exerciseId: String): Flow<Resource<ResultsExercise>> = flow{
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