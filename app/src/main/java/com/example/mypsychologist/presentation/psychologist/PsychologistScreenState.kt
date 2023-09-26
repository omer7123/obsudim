package com.example.mypsychologist.presentation.psychologist

import com.example.mypsychologist.domain.entity.PsychologistData

sealed interface PsychologistScreenState {
    object Init: PsychologistScreenState
    object Loading: PsychologistScreenState
    object Error: PsychologistScreenState
    class Data(val psychologist: PsychologistData): PsychologistScreenState
}