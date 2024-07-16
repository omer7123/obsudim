package com.example.mypsychologist.ui.psychologist

import com.example.mypsychologist.domain.entity.psychologistsEntity.TaskEntity
import com.example.mypsychologist.ui.DelegateItem

class TaskFromPsychologistDelegateItem(private val value: TaskEntity): DelegateItem {
    override fun content(): Any =
        value

    override fun id(): Int =
        value.testId.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean =
        (other as TaskFromPsychologistDelegateItem).content() == content()
}