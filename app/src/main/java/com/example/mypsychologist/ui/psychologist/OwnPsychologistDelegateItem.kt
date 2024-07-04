package com.example.mypsychologist.ui.psychologist

import com.example.mypsychologist.domain.entity.PsychologistWithTaskCount
import com.example.mypsychologist.domain.entity.psychologistsEntity.ManagerEntity
import com.example.mypsychologist.ui.DelegateItem

class OwnPsychologistDelegateItem(
    private val value: ManagerEntity
) : DelegateItem  {

    override fun content(): ManagerEntity =
        value

    override fun id(): Int =
        value.id.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean =
        (other as OwnPsychologistDelegateItem).content() == content()
}

//fun ManagerEntity.toDelegateItem() =
//    OwnPsychologistDelegateItem()
//    map { OwnPsychologistDelegateItem(it.key, it.value) }