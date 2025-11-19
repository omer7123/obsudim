package com.obsudim.mypsychologist.presentation.exercises.exercisePassingFragment

import com.obsudim.mypsychologist.domain.entity.exerciseEntity.PagesExerciseEntity

sealed interface ExercisePassingScreenState {
    data class Content(
        val currentPage: Int,
        val pagesWithFields: List<PagesExerciseEntity>,
    ) : ExercisePassingScreenState

    data object Initial : ExercisePassingScreenState
    data object Error : ExercisePassingScreenState
    data object Loading : ExercisePassingScreenState

}
