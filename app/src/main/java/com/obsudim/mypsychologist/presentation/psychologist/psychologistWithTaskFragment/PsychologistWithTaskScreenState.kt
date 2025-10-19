package com.obsudim.mypsychologist.presentation.psychologist.psychologistWithTaskFragment

import com.obsudim.mypsychologist.ui.core.delegateItems.DelegateItem

sealed class PsychologistWithTaskScreenState {
    data object Init: PsychologistWithTaskScreenState()
    data object Loading: PsychologistWithTaskScreenState()
    data class Content(val items: List<DelegateItem>): PsychologistWithTaskScreenState()
    data object PlaceHolderTaskState: PsychologistWithTaskScreenState()
    data object PlaceHolderPsychologistsState: PsychologistWithTaskScreenState()
    data class Error(val msg: String): PsychologistWithTaskScreenState()
}