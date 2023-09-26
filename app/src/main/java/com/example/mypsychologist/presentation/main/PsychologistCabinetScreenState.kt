package com.example.mypsychologist.presentation.main

import com.example.mypsychologist.domain.entity.ClientRequestEntity

sealed interface PsychologistCabinetScreenState {
    object Init : PsychologistCabinetScreenState
    object Loading : PsychologistCabinetScreenState
    class Data(val requests: List<ClientRequestEntity>) : PsychologistCabinetScreenState
    object Error : PsychologistCabinetScreenState
}