package com.example.mypsychologist.ui.education

import com.example.mypsychologist.domain.entity.educationEntity.ItemMaterialEntity
import com.example.mypsychologist.domain.entity.educationEntity.SubtopicEntity
import com.example.mypsychologist.ui.DelegateItem

class TextCardDelegateItem(private val value: ItemMaterialEntity) : DelegateItem {
    override fun content(): ItemMaterialEntity =
        value

    override fun id(): Int =
        value.id.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean =
        (other as TextCardDelegateItem).content() == content()
}

fun List<SubtopicEntity>.toCardDelegateItems() = run {
    val newList = mutableListOf<DelegateItem>()
    map { subtopic ->
    //    newList.add(subtopic.subtitle.toDelegateItem()) - нужно создать Delegate для этого по аналогии с TextCardDelegateItem
        newList.addAll(subtopic.cards.toDelegateItems())
    }
    newList
}

fun List<ItemMaterialEntity>.toDelegateItems() =
    map { TextCardDelegateItem(it) }