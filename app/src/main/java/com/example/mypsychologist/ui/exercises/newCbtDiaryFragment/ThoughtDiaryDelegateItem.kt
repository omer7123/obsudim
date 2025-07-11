package com.example.mypsychologist.ui.exercises.newCbtDiaryFragment

import com.example.mypsychologist.domain.entity.ThoughtDiaryItemEntity
import com.example.mypsychologist.ui.core.delegateItems.DelegateItem

class ThoughtDiaryDelegateItem (private val value: ThoughtDiaryItemEntity) : DelegateItem {
    override fun content(): ThoughtDiaryItemEntity = value

    override fun id(): Int = value.titleId

    override fun compareToOther(other: DelegateItem): Boolean =
        (other as ThoughtDiaryDelegateItem).content() == content()
}