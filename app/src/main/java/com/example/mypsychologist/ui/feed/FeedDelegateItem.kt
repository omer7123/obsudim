package com.example.mypsychologist.ui.feed

import com.example.mypsychologist.domain.entity.FeedItemUI
import com.example.mypsychologist.ui.DelegateItem

class FeedDelegateItem(private val value: FeedItemUI) : DelegateItem {

    override fun content(): FeedItemUI = value

    override fun id(): Int = value.id.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean =
        (other as FeedDelegateItem).content() == content()
}

fun List<FeedItemUI>.toDelegateItem() =
    map { FeedDelegateItem(it) }