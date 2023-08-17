package com.example.mypsychologist.presentation

import com.example.mypsychologist.domain.entity.ClientInfoEntity

sealed interface EditScreenState {
    object Init : EditScreenState
    class CurrentData(val data: ClientInfoEntity) : EditScreenState
    class Response(val result: Boolean) : EditScreenState
    object Loading: EditScreenState
}