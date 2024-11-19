package com.example.mypsychologist.ui.exercises.cbt

import com.example.mypsychologist.ui.DelegateItem

class HintDelegateItem(private val value: String) : DelegateItem {
    override fun content(): String = value

    override fun id(): Int = value.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean =
        (other as HintDelegateItem).content() == content()
}