package com.example.mypsychologist.ui.exercises.rebt

import com.example.mypsychologist.domain.entity.AutoDialogMessageEntity
import com.example.mypsychologist.ui.DelegateItem

class MessageDelegateItem(
    private val id: String,
    private val value: AutoDialogMessageEntity
) : DelegateItem {
    override fun content(): Pair<String, AutoDialogMessageEntity> =
        Pair(id, value)

    override fun id(): Int = id.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean =
        (other as MessageDelegateItem).content() == content()
}

fun HashMap<String, AutoDialogMessageEntity>.toDelegateItems() =
    map { MessageDelegateItem(it.key, it.value) }