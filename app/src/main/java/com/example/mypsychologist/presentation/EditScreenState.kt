package com.example.mypsychologist.presentation

import com.example.mypsychologist.domain.entity.ClientDataEntity

sealed interface EditScreenState {
    object Init : EditScreenState
    class CurrentData(val data: ClientDataEntity) : EditScreenState
    class Response(val result: Boolean) : EditScreenState
    object Loading: EditScreenState
}