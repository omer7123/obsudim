package com.example.mypsychologist.ui.exercises.cbt

import com.example.mypsychologist.domain.entity.DiaryRecordEntity
import com.example.mypsychologist.ui.DelegateItem

class RecordDelegateItem(
    private val value: DiaryRecordEntity
) : DelegateItem {
    override fun content(): Any = value

    override fun id(): Int = value.id

    override fun compareToOther(other: DelegateItem): Boolean =
        (other as RecordDelegateItem).content() == content()

}

fun List<DiaryRecordEntity>.toDelegateItems() =
    map { RecordDelegateItem(it) }