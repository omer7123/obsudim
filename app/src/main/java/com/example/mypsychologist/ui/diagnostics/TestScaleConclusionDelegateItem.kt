package com.example.mypsychologist.ui.diagnostics

import com.example.mypsychologist.domain.entity.diagnosticEntity.ScaleResultForHistoryEntity
import com.example.mypsychologist.domain.entity.diagnosticEntity.TestResultsGetEntity
import com.example.mypsychologist.ui.DelegateItem

class TestScaleConclusionDelegateItem(private val value: ScaleResultForHistoryEntity) :
    DelegateItem {

    override fun content(): Any = value

    override fun id(): Int = value.scaleId.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean =
        (other as TestScaleConclusionDelegateItem).content() == content()
}

fun TestResultsGetEntity.toConclusionDelegateItems() =
    this.scaleResults.map { TestScaleConclusionDelegateItem(it) }