package com.example.mypsychologist.ui.diagnostics

import com.example.mypsychologist.domain.entity.diagnosticEntity.TestEntity
import com.example.mypsychologist.ui.DelegateItem

class TestWithoutCategoryDelegateItem(
    private val value: TestEntity
): DelegateItem {
    override fun content(): Any = value

    override fun id(): Int = value.testId.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean =
        (other as TestWithoutCategoryDelegateItem).content() == content()

}