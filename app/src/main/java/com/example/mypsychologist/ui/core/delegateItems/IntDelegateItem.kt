package com.example.mypsychologist.ui.core.delegateItems

import com.example.mypsychologist.domain.entity.InputIntExerciseEntity

class IntDelegateItem(private val item: InputIntExerciseEntity) : DelegateItem {

    override fun content(): InputIntExerciseEntity = item

    override fun id(): Int = item.id.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean =
        (other as IntDelegateItem).content() == content()
}