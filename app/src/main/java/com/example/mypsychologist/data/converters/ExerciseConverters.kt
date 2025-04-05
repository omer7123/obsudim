package com.example.mypsychologist.data.converters

import com.example.mypsychologist.data.model.exerciseModels.DailyExerciseModel
import com.example.mypsychologist.data.model.exerciseModels.DailyTaskMarkIdModel
import com.example.mypsychologist.data.model.exerciseModels.DefinitionProblemGroupExerciseModel
import com.example.mypsychologist.data.model.exerciseModels.DefinitionProblemGroupHistoryModel
import com.example.mypsychologist.data.model.exerciseModels.ExerciseDetailModel
import com.example.mypsychologist.data.model.exerciseModels.ExerciseDetailResultModel
import com.example.mypsychologist.data.model.exerciseModels.ExerciseResultFromAPIModel
import com.example.mypsychologist.data.model.exerciseModels.ExerciseResultModel
import com.example.mypsychologist.data.model.exerciseModels.ExerciseResultRequestModel
import com.example.mypsychologist.data.model.exerciseModels.ExercisesModel
import com.example.mypsychologist.data.model.exerciseModels.ExercisesStatusModel
import com.example.mypsychologist.data.model.exerciseModels.FieldExerciseModel
import com.example.mypsychologist.domain.entity.exerciseEntity.DailyExerciseEntity
import com.example.mypsychologist.domain.entity.exerciseEntity.DailyTaskMarkIdEntity
import com.example.mypsychologist.domain.entity.exerciseEntity.DefinitionProblemGroupExerciseEntity
import com.example.mypsychologist.domain.entity.exerciseEntity.ExerciseDetailEntity
import com.example.mypsychologist.domain.entity.exerciseEntity.ExerciseDetailResultEntity
import com.example.mypsychologist.domain.entity.exerciseEntity.ExerciseEntity
import com.example.mypsychologist.domain.entity.exerciseEntity.ExerciseResultEntity
import com.example.mypsychologist.domain.entity.exerciseEntity.ExerciseResultFromAPIEntity
import com.example.mypsychologist.domain.entity.exerciseEntity.ExerciseResultRequestEntity
import com.example.mypsychologist.domain.entity.exerciseEntity.ExercisesStatusEntity
import com.example.mypsychologist.domain.entity.exerciseEntity.FieldExerciseEntity
import com.example.mypsychologist.domain.entity.exerciseEntity.RecordExerciseEntity
import com.example.mypsychologist.domain.entity.exerciseEntity.TypeOfExercise
import com.example.mypsychologist.extensions.convertLondonTimeToDeviceTime

fun ExercisesModel.toEntity(): ExerciseEntity {
    return ExerciseEntity(id, title, description, "https://xn--b1afb6bcb.xn--c1ajjlbco7a.xn----gtbbcb4bjf2ak.xn--p1ai$linkToPicture", closed)
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

fun ExerciseResultRequestEntity.toModel(): ExerciseResultRequestModel {
    return ExerciseResultRequestModel(
        id, result = result.map { it.toModel() }
    )
}

private fun ExerciseResultEntity.toModel() : ExerciseResultModel {
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

fun ExercisesStatusModel.toEntity() = ExercisesStatusEntity(title, isClosed)

fun DefinitionProblemGroupExerciseEntity.toModel() = DefinitionProblemGroupExerciseModel(sphere, emotion, target)

fun DefinitionProblemGroupHistoryModel.toEntity() = RecordExerciseEntity(id, sphere, time.convertLondonTimeToDeviceTime())

