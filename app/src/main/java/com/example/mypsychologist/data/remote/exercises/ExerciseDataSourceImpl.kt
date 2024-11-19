package com.example.mypsychologist.data.remote.exercises

import com.example.mypsychologist.core.BaseDataSource
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.model.ExerciseDetailModel
import com.example.mypsychologist.data.model.ExercisesModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ExerciseDataSourceImpl @Inject constructor(private val api: ExerciseService) :
    BaseDataSource(),
    ExerciseDataSource {

    override suspend fun getAllExercises(): Flow<Resource<List<ExercisesModel>>> = flow {
        emit(getResult {
            api.getAllExercises()
        })
    }.flowOn(Dispatchers.IO)

    override suspend fun getExerciseDetail(id: String): Flow<Resource<ExerciseDetailModel>> = flow{
        emit(
            getResult {
                api.getExerciseDetail(id)
            }
        )
    }.flowOn(Dispatchers.IO)
}