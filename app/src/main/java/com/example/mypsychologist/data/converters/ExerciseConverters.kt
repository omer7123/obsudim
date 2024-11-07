package com.example.mypsychologist.data.converters

import com.example.mypsychologist.data.model.ExercisesModel
import com.example.mypsychologist.domain.entity.exerciseEntity.ExerciseEntity

fun ExercisesModel.toEntity(): ExerciseEntity {
    return ExerciseEntity(id, title, description)
}