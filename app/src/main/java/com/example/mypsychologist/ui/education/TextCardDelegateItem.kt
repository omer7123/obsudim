package com.example.mypsychologist.ui.education

import com.example.mypsychologist.domain.entity.educationEntity.ItemMaterialEntity
import com.example.mypsychologist.ui.DelegateItem

class TextCardDelegateItem(private val value: ItemMaterialEntity) : DelegateItem {
    override fun content(): ItemMaterialEntity =
        value

    override fun id(): Int =
        value.id.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean =
        (other as TextCardDelegateItem).content() == content()
}

fun List<ItemMaterialEntity>.toDelegateItems() =
    map { TextCardDelegateItem(it) }