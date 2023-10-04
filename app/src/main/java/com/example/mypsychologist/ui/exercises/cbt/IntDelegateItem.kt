package com.example.mypsychologist.ui.exercises.cbt

import com.example.mypsychologist.ui.DelegateItem

class IntDelegateItem(private val value: Int) : DelegateItem {

    override fun content(): Int = value

    override fun id(): Int = value

    override fun compareToOther(other: DelegateItem): Boolean =
        (other as IntDelegateItem).content() == content()
}