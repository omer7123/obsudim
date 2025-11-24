package com.obsudim.mypsychologist.data.converters

import com.obsudim.mypsychologist.data.model.exerciseModels.DailyExerciseModel
import com.obsudim.mypsychologist.data.model.exerciseModels.DailyTaskMarkIdModel
import com.obsudim.mypsychologist.data.model.exerciseModels.ExerciseDetailModel
import com.obsudim.mypsychologist.data.model.exerciseModels.ExerciseDetailResultModel
import com.obsudim.mypsychologist.data.model.exerciseModels.ExerciseInfoPreview
import com.obsudim.mypsychologist.data.model.exerciseModels.ExerciseResultFromAPIModel
import com.obsudim.mypsychologist.data.model.exerciseModels.ExerciseResultModel
import com.obsudim.mypsychologist.data.model.exerciseModels.ExerciseResultRequestModel
import com.obsudim.mypsychologist.data.model.exerciseModels.ExercisesModel
import com.obsudim.mypsychologist.data.model.exerciseModels.ExercisesStatusModel
import com.obsudim.mypsychologist.data.model.exerciseModels.PagesExerciseModel
import com.obsudim.mypsychologist.data.model.exerciseModels.SectionsExerciseModel
import com.obsudim.mypsychologist.data.model.exerciseModels.TypeFieldModel
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.DailyExerciseEntity
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.DailyTaskMarkIdEntity
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.ExerciseAllResultEntity
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.ExerciseDetailEntity
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.ExerciseDetailResultEntity
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.ExerciseEntity
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.ExerciseInfoPreviewEntity
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.ExerciseResultEntity
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.ExerciseResultRequestEntity
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.ExercisesStatusEntity
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.PagesExerciseEntity
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.SectionsExerciseEntity
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.TypeOfSection
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.TypeOfSectionUiRes

fun ExerciseInfoPreview.toEntity(): ExerciseInfoPreviewEntity {
    return ExerciseInfoPreviewEntity(id, title, description, timeToRead, questionsCount)
}

fun ExercisesModel.toEntity(): ExerciseEntity {
    return ExerciseEntity(id, title, "https://xn--b1afb6bcb.xn--d1acsjd4h.tech$linkToPicture", open)
}

fun ExerciseDetailModel.toEntity(): ExerciseDetailEntity =
    ExerciseDetailEntity(
        pulledFields = pulledFields,
        id = id,
        title = title,
        pictureLink = pictureLink,
        description = description,
        timeToRead = timeToRead,
        questionsCount = questionsCount,
        open = open,
        pages = pages.map { it.toEntity() },
        )

fun PagesExerciseModel.toEntity(): PagesExerciseEntity {
    return PagesExerciseEntity(pageNumber = pageNumber, sections = sections.map { it.toEntity() })
}

fun SectionsExerciseModel.toEntity(): SectionsExerciseEntity{
    val typeLoc = when(this.type){
        "input"-> TypeOfSection.TextInput
        else -> TypeOfSection.AddableList
    }

    return SectionsExerciseEntity(
        id = id,
        title = title,
        view = view,
        type = typeLoc,
        placeholder = placeholder,
        prompt = prompt,
        variants = variants
    )
}

fun ExerciseResultRequestEntity.toModel(): ExerciseResultRequestModel {
    return ExerciseResultRequestModel(
        id = id,
        filledFields = filledFields.map{it.toModel()}
    )
}

private fun TypeOfSectionUiRes.toModel(): TypeFieldModel{
    return when(val type = this){
        is TypeOfSectionUiRes.AddableListUiEntity -> TypeFieldModel.AddableListModel(fieldId = type.id, text = type.list)
        is TypeOfSectionUiRes.TextInputUiEntity -> TypeFieldModel.InputTextModel(fieldId = type.id, text = type.title.orEmpty())
    }
}

fun DailyExerciseModel.toEntity(): DailyExerciseEntity{
    return DailyExerciseEntity(id, type, title, shortDescription, destinationId, isComplete)
}

fun DailyTaskMarkIdEntity.toModel() = DailyTaskMarkIdModel(
    id = id
)

fun ExerciseResultFromAPIModel.toEntity() = ExerciseAllResultEntity(id, exerciseId, date, preview)

fun ExerciseDetailResultModel.toEntity() =
    ExerciseDetailResultEntity(id, title, pictureLink, description, exerciseId, date, sections.map { it.toEntity() })

private fun ExerciseResultModel.toEntity(): ExerciseResultEntity {
    val typeEntity = when (type) {
        "input" -> TypeOfSection.TextInput
        "addable_list" -> TypeOfSection.AddableList
        else -> TypeOfSection.TextInput
    }
    return ExerciseResultEntity(title, view, typeEntity, value)
}

fun ExercisesStatusModel.toEntity() = ExercisesStatusEntity(title, isClosed)

