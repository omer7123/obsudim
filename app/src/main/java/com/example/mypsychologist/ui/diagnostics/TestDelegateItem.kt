package com.example.mypsychologist.ui.diagnostics

import com.example.mypsychologist.domain.entity.TestCardEntity
import com.example.mypsychologist.ui.DelegateItem

class TestDelegateItem(
    private val value: TestCardEntity
) : DelegateItem {
    override fun content(): Any = value

    override fun id(): Int = value.titleId

    override fun compareToOther(other: DelegateItem): Boolean =
        (other as TestDelegateItem).content() == content()
}

fun List<TestCardEntity>.toDelegateItems() =
    map { TestDelegateItem(it)}