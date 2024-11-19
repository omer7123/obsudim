package com.example.mypsychologist.ui.exercises.cbt

import com.example.mypsychologist.domain.entity.InputIntExerciseEntity
import com.example.mypsychologist.ui.DelegateItem

class IntDelegateItem(private val item: InputIntExerciseEntity) : DelegateItem {

    override fun content(): InputIntExerciseEntity = item

    override fun id(): Int = item.id.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean =
        (other as IntDelegateItem).content() == content()
}