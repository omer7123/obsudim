package com.example.mypsychologist.ui.education

import com.example.mypsychologist.domain.entity.EducationTopicEntity
import com.example.mypsychologist.ui.DelegateItem

class TopicDelegateItem(private val value: EducationTopicEntity) : DelegateItem {

    override fun content(): EducationTopicEntity =
        value

    override fun id(): Int =
        value.titleId

    override fun compareToOther(other: DelegateItem): Boolean =
        (other as TopicDelegateItem).content() == content()
}

fun List<EducationTopicEntity>.toDelegateItems() =
    map { TopicDelegateItem(it) }
