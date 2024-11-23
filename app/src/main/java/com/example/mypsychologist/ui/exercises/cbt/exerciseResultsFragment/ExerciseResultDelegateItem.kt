package com.example.mypsychologist.ui.exercises.cbt.exerciseResultsFragment

import com.example.mypsychologist.domain.entity.exerciseEntity.ExerciseResultFromAPIEntity
import com.example.mypsychologist.ui.DelegateItem

class ExerciseResultDelegateItem(
    private val value: ExerciseResultFromAPIEntity
) : DelegateItem{
    override fun content(): Any {
        return value
    }

    override fun id(): Int {
        return value.completedExerciseId.hashCode()
    }

    override fun compareToOther(other: DelegateItem): Boolean =
        (other as ExerciseResultDelegateItem).content() == content()
}

fun ExerciseResultFromAPIEntity.toDelegateItems(): ExerciseResultDelegateItem =
    ExerciseResultDelegateItem(value = this)