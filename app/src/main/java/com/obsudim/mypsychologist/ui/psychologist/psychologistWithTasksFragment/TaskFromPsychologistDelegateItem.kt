package com.obsudim.mypsychologist.ui.psychologist.psychologistWithTasksFragment

import com.obsudim.mypsychologist.domain.entity.psychologistsEntity.TaskEntity
import com.obsudim.mypsychologist.ui.core.delegateItems.DelegateItem

class TaskFromPsychologistDelegateItem(private val value: TaskEntity): DelegateItem {
    override fun content(): Any =
        value

    override fun id(): Int =
        value.testId.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean =
        (other as TaskFromPsychologistDelegateItem).content() == content()
}