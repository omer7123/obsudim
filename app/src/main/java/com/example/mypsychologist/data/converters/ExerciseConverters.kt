package com.example.mypsychologist.data.converters

import com.example.mypsychologist.data.model.DailyExerciseModel
import com.example.mypsychologist.data.model.DailyTaskMarkIdModel
import com.example.mypsychologist.data.model.ExerciseDetailModel
import com.example.mypsychologist.data.model.ExerciseDetailResultModel
import com.example.mypsychologist.data.model.ExerciseResultFromAPIModel
import com.example.mypsychologist.data.model.ExerciseResultModel
import com.example.mypsychologist.data.model.ExerciseResultRequestModel
import com.example.mypsychologist.data.model.ExercisesModel
import com.example.mypsychologist.data.model.FieldExerciseModel
import com.example.mypsychologist.domain.entity.exerciseEntity.DailyExerciseEntity
import com.example.mypsychologist.domain.entity.exerciseEntity.DailyTaskMarkIdEntity
import com.example.mypsychologist.domain.entity.exerciseEntity.ExerciseDetailEntity
import com.example.mypsychologist.domain.entity.exerciseEntity.ExerciseDetailResultEntity
import com.example.mypsychologist.domain.entity.exerciseEntity.ExerciseEntity
import com.example.mypsychologist.domain.entity.exerciseEntity.ExerciseResultEntity
import com.example.mypsychologist.domain.entity.exerciseEntity.ExerciseResultFromAPIEntity
import com.example.mypsychologist.domain.entity.exerciseEntity.ExerciseResultRequestEntity
import com.example.mypsychologist.domain.entity.exerciseEntity.FieldExerciseEntity
import com.example.mypsychologist.domain.entity.exerciseEntity.TypeOfExercise

fun ExercisesModel.toEntity(): ExerciseEntity {
    return ExerciseEntity(id, title, description)
}

fun ExerciseDetailModel.toEntity(): ExerciseDetailEntity =
    ExerciseDetailEntity(id, title, description, fields = field.map { it.toEntity() })

fun FieldExerciseModel.toEntity(): FieldExerciseEntity {
    val typeOfEntity = when (type) {
        1 -> TypeOfExercise.TextInput
        2 -> TypeOfExercise.NumberInput
        else -> TypeOfExercise.TextInput
    }
    return FieldExerciseEntity(description, title, major, exerciseStructureId, typeOfEntity, id)
}

fun ExerciseResultRequestEntity.toModel(): ExerciseResultRequestModel{
    return ExerciseResultRequestModel(
        id, result = result.map { it.toModel() }
    )
}

private fun ExerciseResultEntity.toModel() :ExerciseResultModel{
    return ExerciseResultModel(
        fieldId = fieldId,
        value = value
    )
}

fun DailyExerciseModel.toEntity(): DailyExerciseEntity{
    return DailyExerciseEntity(id, type, title, shortDescription, destinationId, isComplete)
}

fun DailyTaskMarkIdEntity.toModel() = DailyTaskMarkIdModel(
    id = id
)

fun ExerciseResultFromAPIModel.toEntity() = ExerciseResultFromAPIEntity(completedExerciseId, date)

fun ExerciseDetailResultModel.toEntity() =
    ExerciseDetailResultEntity(title, date, result = result.map { ExerciseResultEntity(fieldId = it.fieldId, value = it.value) })

