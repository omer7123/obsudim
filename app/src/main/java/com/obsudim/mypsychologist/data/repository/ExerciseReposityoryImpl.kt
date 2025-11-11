package com.obsudim.mypsychologist.data.repository

import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.data.converters.toEntity
import com.obsudim.mypsychologist.data.converters.toModel
import com.obsudim.mypsychologist.data.remote.exercises.ExerciseDataSource
import com.obsudim.mypsychologist.domain.entity.ThoughtDiaryEntity
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.DailyExerciseEntity
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.DailyTaskMarkIdEntity
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.ExerciseAllResultEntity
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.ExerciseDetailEntity
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.ExerciseDetailResultEntity
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.ExerciseEntity
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.ExerciseInfoPreviewEntity
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.ExerciseResultRequestEntity
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.ExercisesStatusEntity
import com.obsudim.mypsychologist.domain.repository.retrofit.ExerciseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExerciseReposityoryImpl @Inject constructor(private val dataSource: ExerciseDataSource) :
    ExerciseRepository {
    override suspend fun getExerciseInfoPreview(id: String): Flow<Resource<ExerciseInfoPreviewEntity>> {
        return dataSource.getExerciseInfoPreview(id).checkResource {
            it.toEntity()
        }
    }

    override suspend fun getAllExercises(): Flow<Resource<List<ExerciseEntity>>> {
        return dataSource.getAllExercises().checkResource { list ->
            list.exercises.map { it.toEntity() }
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

    override suspend fun getExerciseResults(exerciseId: String): Flow<Resource<List<ExerciseAllResultEntity>>> {
        return dataSource.getExerciseResults(exerciseId).checkResource {list->
            list.results.map { it.toEntity() }
        }
    }

    override suspend fun getExerciseDetailResult(id: String): Flow<Resource<ExerciseDetailResultEntity>> {
        return dataSource.getExerciseDetailResult(id).checkResource {
            it.toEntity()
        }
    }

    override suspend fun getAllStatusExercise(): Flow<Resource<List<ExercisesStatusEntity>>> {
        return dataSource.getAllStatusExercises().checkResource {list->
            list.map {
                it.toEntity()
            }
        }
    }

    override suspend fun saveCBTDiary(diary: ThoughtDiaryEntity): Flow<Resource<DailyTaskMarkIdEntity>> {
        return dataSource.saveCBTDiary(diary.toModel()).checkResource {
            DailyTaskMarkIdEntity(it.id)
        }
    }
}