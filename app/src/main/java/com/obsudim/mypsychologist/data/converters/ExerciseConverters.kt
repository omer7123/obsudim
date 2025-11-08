package com.obsudim.mypsychologist.data.converters

import com.obsudim.mypsychologist.data.model.exerciseModels.DailyExerciseModel
import com.obsudim.mypsychologist.data.model.exerciseModels.DailyTaskMarkIdModel
import com.obsudim.mypsychologist.data.model.exerciseModels.DefinitionProblemGroupExerciseModel
import com.obsudim.mypsychologist.data.model.exerciseModels.DefinitionProblemGroupHistoryModel
import com.obsudim.mypsychologist.data.model.exerciseModels.ExerciseDetailModel
import com.obsudim.mypsychologist.data.model.exerciseModels.ExerciseDetailResultModel
import com.obsudim.mypsychologist.data.model.exerciseModels.ExerciseInfoPreview
import com.obsudim.mypsychologist.data.model.exerciseModels.ExerciseResultFromAPIModel
import com.obsudim.mypsychologist.data.model.exerciseModels.ExerciseResultModel
import com.obsudim.mypsychologist.data.model.exerciseModels.ExerciseResultRequestModel
import com.obsudim.mypsychologist.data.model.exerciseModels.ExercisesModel
import com.obsudim.mypsychologist.data.model.exerciseModels.ExercisesStatusModel
import com.obsudim.mypsychologist.data.model.exerciseModels.FieldExerciseModel
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.DailyExerciseEntity
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.DailyTaskMarkIdEntity
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.DefinitionProblemGroupExerciseEntity
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.ExerciseDetailEntity
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.ExerciseDetailResultEntity
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.ExerciseEntity
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.ExerciseInfoPreviewEntity
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.ExerciseResultEntity
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.ExerciseResultFromAPIEntity
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.ExerciseResultRequestEntity
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.ExercisesStatusEntity
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.FieldExerciseEntity
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.RecordExerciseEntity
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.TypeOfExercise
import com.obsudim.mypsychologist.extensions.convertLondonTimeToDeviceTime

fun ExerciseInfoPreview.toEntity(): ExerciseInfoPreviewEntity {
    return ExerciseInfoPreviewEntity(id, title, description, timeToRead, questionsCount)
}

fun ExercisesModel.toEntity(): ExerciseEntity {
    return ExerciseEntity(id, title, "https://xn--b1afb6bcb.xn--d1acsjd4h.tech$linkToPicture", closed)
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

