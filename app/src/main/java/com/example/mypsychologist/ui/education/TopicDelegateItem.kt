package com.example.mypsychologist.ui.education

import com.example.mypsychologist.domain.entity.educationEntity.TopicEntity
import com.example.mypsychologist.ui.DelegateItem

class TopicDelegateItem(private val value: TopicEntity) : DelegateItem {

    override fun content(): TopicEntity =
        value

    override fun id(): Int =
        value.id.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean =
        (other as TopicDelegateItem).content() == content()
}

fun List<TopicEntity>.toDelegateItems() =
    map { TopicDelegateItem(it) }
