package com.obsudim.mypsychologist.domain.entity.exerciseEntity

import kotlinx.serialization.SerialName

data class ExerciseInfoPreviewEntity(
    val id: String,
    val title: String,
    val description: String,
    val timeToRead: Int,
    val questionsCount: Int,
)
data class ExerciseEntity(
    val id: String,
    val title: String,
    val linkToPicture: String,
    val open: Boolean
)

data class ExerciseDetailEntity(
    val pulledFields: List<String>,
    val id: String,
    val title: String,
    val pictureLink: String,
    val description: String,
    val timeToRead: Int,
    val questionsCount: Int,
    val open: Boolean,
    val pages: List<PagesExerciseEntity>
)

data class PagesExerciseEntity(
    val pageNumber: Int,
    val sections: List<SectionsExerciseEntity>,
)

data class SectionsExerciseEntity(
    val id: String,
    val title: String,
    val view: String,
    val type: TypeOfSection,
    val placeholder: String,
    val prompt: String,
    val variants: List<String>,
)

sealed interface TypeOfSection {
    data object TextInput : TypeOfSection
    data object AddableList : TypeOfSection
}

sealed class TypeOfSectionUiRes(val id: String) {
    data class TextInputUiEntity(
        val idLoc: String,
        val title: String? = null,
    ) : TypeOfSectionUiRes(idLoc)

    data class AddableListUiEntity(
        val idLoc: String,
        val list: List<String>,
    ): TypeOfSectionUiRes(idLoc)
}

data class FieldAddableListChange(
    val idField: String,
    val idItem: Int,
    val text: String,
)

data class ExerciseAllResultEntity(
    val id: String,
    val exerciseId: String,
    val date: String,
    val preview: String,
)

data class ExerciseResultRequestEntity(
    val id: String,
    val filledFields: List<TypeOfSectionUiRes>
)

data class DailyExerciseEntity(
    val id: String,
    val type: Int,
    val title: String,
    val shortDescription: String,
    val destinationId: String,
    val isComplete: Boolean
)

data class DailyTaskMarkIdEntity(
    val id: String
)

data class SaveExerciseResultResponseEntity(
    val id: String,
    val score: Int,
    val pictureLink: String,
    val view: String,
    val successMessage: String,
)

data class ExerciseResultEntity(
    val title: String,
    val view: String,
    val type: TypeOfSection,
    val value: String
)

data class ExerciseDetailResultEntity(
    val id: String,
    val title: String,
    val pictureLink: String,
    val description: String,
    @SerialName("exercise_id")
    val exerciseId: String,
    val date: String,
    val sections: List<ExerciseResultEntity>
)


data class RecordExerciseEntity(
    val id: String,
    val title: String,
    val date: String
)

data class ExercisesStatusEntity(
    val title: String,
    val isClosed: Boolean,
)