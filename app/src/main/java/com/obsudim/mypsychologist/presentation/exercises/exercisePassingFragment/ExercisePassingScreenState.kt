package com.obsudim.mypsychologist.presentation.exercises.exercisePassingFragment

import com.obsudim.mypsychologist.domain.entity.exerciseEntity.PagesExerciseEntity
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.TypeOfSectionUiRes

sealed interface ExercisePassingScreenState {
    data class Content(
        val currentPage: Int,
        val pagesWithFields: List<PagesExerciseEntity>,
        val currValue: List<TypeOfSectionUiRes>
    ) : ExercisePassingScreenState

    data object Initial : ExercisePassingScreenState
    data object Error : ExercisePassingScreenState
    data object Loading : ExercisePassingScreenState
    data object SuccessSave: ExercisePassingScreenState

}
