package com.example.mypsychologist.presentation.exercises.exercisesFragment

import com.example.mypsychologist.domain.entity.exerciseEntity.ExerciseDetailWithDelegateItem

sealed interface NewExerciseScreenState{
    data object Init: NewExerciseScreenState
    data class Error(val msg: String): NewExerciseScreenState
    data class Content(val data: ExerciseDetailWithDelegateItem): NewExerciseScreenState
    data object Loading: NewExerciseScreenState
}