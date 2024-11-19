package com.example.mypsychologist.data.converters

import com.example.mypsychologist.data.model.ExerciseDetailModel
import com.example.mypsychologist.data.model.ExercisesModel
import com.example.mypsychologist.data.model.FieldExerciseModel
import com.example.mypsychologist.domain.entity.exerciseEntity.ExerciseDetailEntity
import com.example.mypsychologist.domain.entity.exerciseEntity.ExerciseEntity
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

