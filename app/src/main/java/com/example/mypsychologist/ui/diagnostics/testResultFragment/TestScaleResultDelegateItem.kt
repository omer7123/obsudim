package com.example.mypsychologist.ui.diagnostics.testResultFragment

import com.example.mypsychologist.domain.entity.diagnosticEntity.ScaleResultForHistoryEntity
import com.example.mypsychologist.domain.entity.diagnosticEntity.TestResultsGetEntity
import com.example.mypsychologist.ui.core.delegateItems.DelegateItem


class TestScaleResultDelegateItem (private val value: ScaleResultForHistoryEntity) : DelegateItem {

    override fun content(): Any = value

    override fun id(): Int = value.scaleId.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean =
        (other as TestScaleResultDelegateItem).content() == content()
}

fun TestResultsGetEntity.toDelegateItems() =
    this.scaleResults.map { TestScaleResultDelegateItem(it) }
