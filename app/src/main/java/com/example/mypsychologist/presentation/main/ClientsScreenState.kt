package com.example.mypsychologist.presentation.main

import com.example.mypsychologist.domain.entity.ClientCardEntity

sealed interface ClientsScreenState {
    object Init: ClientsScreenState
    object Error: ClientsScreenState
    object Loading: ClientsScreenState
    class Data(val items: List<ClientCardEntity>): ClientsScreenState
}