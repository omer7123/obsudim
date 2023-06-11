package com.example.mypsychologist.ui.exercises.cbt

import com.example.mypsychologist.ui.DelegateItem

class RecordDelegateItem(
    private val id: Int,
    private val value: String
) : DelegateItem {
    override fun content(): Any = value

    override fun id(): Int = id

    override fun compareToOther(other: DelegateItem): Boolean =
        (other as RecordDelegateItem).content() == content()

}