package com.example.mypsychologist.ui.exercises.rebt

import com.example.mypsychologist.domain.entity.ProblemEntity
import com.example.mypsychologist.ui.DelegateItem

class ProblemDelegateItem(
    private val id: String,
    private val value: ProblemEntity
) : DelegateItem {
    override fun content(): Any = Pair(id, value)

    override fun id(): Int = id.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean =
        (other as ProblemDelegateItem).content() == content()
}

fun List<ProblemEntity>.toDelegateItems() =
    map { ProblemDelegateItem(it.id, it) }