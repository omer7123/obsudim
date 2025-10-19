package com.obsudim.mypsychologist.ui.diagnostics.testResultFragment

import com.obsudim.mypsychologist.domain.entity.diagnosticEntity.ScaleResultForHistoryEntity
import com.obsudim.mypsychologist.domain.entity.diagnosticEntity.TestResultsGetEntity
import com.obsudim.mypsychologist.ui.core.delegateItems.DelegateItem

class TestScaleConclusionDelegateItem(private val value: ScaleResultForHistoryEntity) :
    DelegateItem {

    override fun content(): Any = value

    override fun id(): Int = value.scaleId.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean =
        (other as TestScaleConclusionDelegateItem).content() == content()
}

fun TestResultsGetEntity.toConclusionDelegateItems() =
    this.scaleResults.map { TestScaleConclusionDelegateItem(it) }