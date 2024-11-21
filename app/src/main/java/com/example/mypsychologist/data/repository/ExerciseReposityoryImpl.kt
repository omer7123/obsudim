package com.example.mypsychologist.data.repository

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.converters.toEntity
import com.example.mypsychologist.data.converters.toModel
import com.example.mypsychologist.data.remote.exercises.ExerciseDataSource
import com.example.mypsychologist.domain.entity.exerciseEntity.DailyExerciseEntity
import com.example.mypsychologist.domain.entity.exerciseEntity.DailyTaskMarkIdEntity
import com.example.mypsychologist.domain.entity.exerciseEntity.ExerciseDetailEntity
import com.example.mypsychologist.domain.entity.exerciseEntity.ExerciseEntity
import com.example.mypsychologist.domain.entity.exerciseEntity.ExerciseResultRequestEntity
import com.example.mypsychologist.domain.repository.retrofit.ExerciseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExerciseReposityoryImpl @Inject constructor(private val dataSource: ExerciseDataSource) :
    ExerciseRepository {
    override suspend fun getAllExercises(): Flow<Resource<List<ExerciseEntity>>> {
        return dataSource.getAllExercises().checkResource { list ->
            list.map { it.toEntity() }
        }
    }

    override suspend fun getExerciseDetail(id: String): Flow<Resource<ExerciseDetailEntity>> {
        return dataSource.getExerciseDetail(id).checkResource { model ->
            model.toEntity()
        }
    }

    override suspend fun getDailyExercises(): Flow<Resource<List<DailyExerciseEntity>>> {
        return dataSource.getDailyExercises().checkResource {list->
            list.map { it.toEntity() }
        }
    }

    override suspend fun markAsCompleteDailyTask(dailyTaskMarkIdEntity: DailyTaskMarkIdEntity): Flow<Resource<String>> {
        return dataSource.markAsCompleteTask(dailyTaskMarkIdEntity.toModel()).checkResource {
            it.status
        }
    }

    override suspend fun saveExerciseResult(result: ExerciseResultRequestEntity): Flow<Resource<DailyTaskMarkIdEntity>> {
        return dataSource.saveExerciseResult(result.toModel()).checkResource {
            DailyTaskMarkIdEntity(it.id)
        }
    }
}