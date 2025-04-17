package com.example.mypsychologist.ui.psychologist.psychologistWithTasksFragment

import com.example.mypsychologist.domain.entity.psychologistsEntity.ManagerEntity
import com.example.mypsychologist.ui.core.delegateItems.DelegateItem

class OwnPsychologistDelegateItem(
    private val value: ManagerEntity
) : DelegateItem {

    override fun content(): ManagerEntity =
        value

    override fun id(): Int =
        value.id.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean =
        (other as OwnPsychologistDelegateItem).content() == content()
}