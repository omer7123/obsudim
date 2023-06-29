package com.example.mypsychologist.presentation

import com.example.mypsychologist.domain.entity.RebtProblemProgressEntity

sealed interface REBTScreenState {
    object Init: REBTScreenState
    object Error: REBTScreenState
    object Loading: REBTScreenState
    class Data(val problemProgress: RebtProblemProgressEntity): REBTScreenState
}