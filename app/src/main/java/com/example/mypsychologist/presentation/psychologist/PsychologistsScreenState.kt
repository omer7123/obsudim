package com.example.mypsychologist.presentation.psychologist

import com.example.mypsychologist.domain.entity.PsychologistCard

sealed interface PsychologistsScreenState {
    object Init: PsychologistsScreenState
    object Error: PsychologistsScreenState
    object Loading: PsychologistsScreenState
    class Data(val items: HashMap<String, PsychologistCard>): PsychologistsScreenState
}