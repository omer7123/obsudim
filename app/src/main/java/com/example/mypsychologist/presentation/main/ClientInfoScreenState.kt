package com.example.mypsychologist.presentation.main

import com.example.mypsychologist.domain.entity.ClientInfoEntity

sealed interface ClientInfoScreenState {
    object Init : ClientInfoScreenState
    object Loading : ClientInfoScreenState
    class Data(val info: ClientInfoEntity) : ClientInfoScreenState
    object Error : ClientInfoScreenState
}