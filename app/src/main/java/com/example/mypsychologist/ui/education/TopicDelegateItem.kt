package com.example.mypsychologist.ui.education

import com.example.mypsychologist.domain.entity.educationEntity.ThemeEntity
import com.example.mypsychologist.ui.DelegateItem

class TopicDelegateItem(private val value: ThemeEntity) : DelegateItem {

    override fun content(): ThemeEntity =
        value

    override fun id(): Int =
        value.id.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean =
        (other as TopicDelegateItem).content() == content()
}

fun List<ThemeEntity>.toDelegateItems() =
    map { TopicDelegateItem(it) }
