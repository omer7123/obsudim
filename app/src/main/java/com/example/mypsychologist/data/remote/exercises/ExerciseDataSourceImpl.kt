package com.example.mypsychologist.data.remote.exercises

import com.example.mypsychologist.core.BaseDataSource
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.model.DailyExerciseModel
import com.example.mypsychologist.data.model.DailyTaskMarkIdModel
import com.example.mypsychologist.data.model.DefinitionProblemGroupExerciseModel
import com.example.mypsychologist.data.model.ExerciseDetailModel
import com.example.mypsychologist.data.model.ExerciseDetailResultModel
import com.example.mypsychologist.data.model.ExerciseResultFromAPIModel
import com.example.mypsychologist.data.model.ExerciseResultRequestModel
import com.example.mypsychologist.data.model.ExerciseSaveResponseModel
import com.example.mypsychologist.data.model.ExercisesModel
import com.example.mypsychologist.data.model.ExercisesStatusModel
import com.example.mypsychologist.data.model.SaveExerciseResultResponseModel
import com.example.mypsychologist.data.model.StatusPostResponse
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

    override suspend fun saveDefinitionProblemGroupResult(definitionData: DefinitionProblemGroupExerciseModel): Flow<Resource<ExerciseSaveResponseModel>> = flow{
        emit(Resource.Loading)
        emit(
            getResult {
                api.saveDefinitionProblemGroupResult(definitionData)
            }
        )
    }.flowOn(Dispatchers.IO)
}