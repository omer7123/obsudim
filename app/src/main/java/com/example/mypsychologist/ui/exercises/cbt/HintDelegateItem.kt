package com.example.mypsychologist.ui.exercises.cbt

import com.example.mypsychologist.domain.entity.InputItemEntity
import com.example.mypsychologist.ui.DelegateItem

class HintDelegateItem(private val value: Int) : DelegateItem {
    override fun content(): Int = value

    override fun id(): Int = value.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean =
        (other as HintDelegateItem).content() == content()
}