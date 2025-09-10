package com.example.mypsychologist.ui.exercises.newCbtDiaryFragment

import com.example.mypsychologist.ui.core.delegateItems.DelegateItem

class SliderDelegateItem(private val value: Int) : DelegateItem {

    override fun content(): Int = value

    override fun id(): Int = value

    override fun compareToOther(other: DelegateItem): Boolean =
        (other as SliderDelegateItem).content() == content()
}