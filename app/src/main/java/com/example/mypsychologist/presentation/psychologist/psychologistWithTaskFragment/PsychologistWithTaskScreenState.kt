package com.example.mypsychologist.presentation.psychologist.psychologistWithTaskFragment

import com.example.mypsychologist.ui.DelegateItem

sealed class PsychologistWithTaskScreenState {
    data object Init: PsychologistWithTaskScreenState()
    data object Loading: PsychologistWithTaskScreenState()
    data class Content(val items: List<DelegateItem>): PsychologistWithTaskScreenState()
    data object PlaceHolderTaskState: PsychologistWithTaskScreenState()
    data object PlaceHolderPsychologistsState: PsychologistWithTaskScreenState()
    data class Error(val msg: String): PsychologistWithTaskScreenState()
}