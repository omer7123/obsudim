package com.example.mypsychologist.ui.exercises.cbt

import com.example.mypsychologist.domain.entity.InputItemEntity
import com.example.mypsychologist.domain.entity.InputItemExerciseEntity
import com.example.mypsychologist.ui.DelegateItem

class InputDelegateItem (private val value: InputItemEntity) : DelegateItem {
    override fun content(): InputItemEntity = value

    override fun id(): Int = value.titleId

    override fun compareToOther(other: DelegateItem): Boolean =
        (other as InputDelegateItem).content() == content()
}

class InputExerciseDelegateItem (private val value: InputItemExerciseEntity) : DelegateItem {
    override fun content(): InputItemExerciseEntity = value

    override fun id(): Int = value.id.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean =
        (other as InputExerciseDelegateItem).content() == content()
}