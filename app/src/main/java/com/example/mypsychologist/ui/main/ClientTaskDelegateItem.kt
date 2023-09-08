package com.example.mypsychologist.ui.main

import com.example.mypsychologist.domain.entity.TaskEntity
import com.example.mypsychologist.ui.DelegateItem

class ClientTaskDelegateItem(private val id: String, private val value: TaskEntity) : DelegateItem {

    override fun content(): Any = Pair(id, value)

    override fun id(): Int = id.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean =
        (other as ClientTaskDelegateItem).content() == content()
}

fun HashMap<String, TaskEntity>.toDelegateItem() =
    map { ClientTaskDelegateItem(it.key, it.value) }