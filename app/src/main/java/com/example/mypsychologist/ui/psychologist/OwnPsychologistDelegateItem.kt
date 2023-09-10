package com.example.mypsychologist.ui.psychologist

import com.example.mypsychologist.domain.entity.PsychologistWithTaskCount
import com.example.mypsychologist.ui.DelegateItem

class OwnPsychologistDelegateItem(
    private val id: String,
    private val value: PsychologistWithTaskCount
) : DelegateItem  {

    override fun content(): Pair<String, PsychologistWithTaskCount> =
        Pair(id, value)

    override fun id(): Int =
        id.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean =
        (other as OwnPsychologistDelegateItem).content() == content()
}

fun HashMap<String, PsychologistWithTaskCount>.toDelegateItem() =
    map { OwnPsychologistDelegateItem(it.key, it.value) }