package com.example.mypsychologist.data.repository

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.converters.toEntity
import com.example.mypsychologist.data.remote.exercises.ExerciseDataSource
import com.example.mypsychologist.domain.entity.exerciseEntity.ExerciseEntity
import com.example.mypsychologist.domain.repository.retrofit.ExerciseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ExerciseReposityoryImpl @Inject constructor(private val dataSource: ExerciseDataSource) :
    ExerciseRepository {
    override suspend fun getAllExercises(): Flow<Resource<List<ExerciseEntity>>> {

        return dataSource.getAllExercises().map { resource ->
            when (resource) {
                is Resource.Error -> Resource.Error(resource.msg, null)
                Resource.Loading -> Resource.Loading
                is Resource.Success -> Resource.Success(resource.data.map { it.toEntity() })
            }
        }
    }
}