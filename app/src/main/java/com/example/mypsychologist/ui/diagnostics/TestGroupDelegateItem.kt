package com.example.mypsychologist.ui.diagnostics

import com.example.mypsychologist.domain.entity.TestGroupEntity
import com.example.mypsychologist.ui.core.delegateItems.DelegateItem

class TestGroupDelegateItem(
    private val value: TestGroupEntity
) : DelegateItem {
    override fun content(): Any = value

    override fun id(): Int = value.titleId

    override fun compareToOther(other: DelegateItem): Boolean =
        (other as TestGroupDelegateItem).content() == content()
}

fun List<TestGroupEntity>.toDelegateItem() =
    map { TestGroupDelegateItem(it) }