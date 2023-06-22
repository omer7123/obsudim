package com.example.mypsychologist.ui.diagnostics

import com.example.mypsychologist.domain.entity.TestGroupEntity
import com.example.mypsychologist.ui.DelegateItem

class TestGroupDelegateItem(
    private val id: Int,
    private val value: TestGroupEntity
) : DelegateItem {
    override fun content(): Any = value

    override fun id(): Int = id

    override fun compareToOther(other: DelegateItem): Boolean =
        (other as TestGroupDelegateItem).content() == content()
}