package com.example.mypsychologist.ui.main

import com.example.mypsychologist.ui.DelegateItem

class StringDelegateItem(private val value: String) : DelegateItem {

    override fun content(): Any = value

    override fun id(): Int = value.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean =
        (other as StringDelegateItem).content() == content()
}

fun List<String>.toDelegateItems() =
    map { StringDelegateItem(it) }