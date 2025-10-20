package com.obsudim.mypsychologist.presentation.core

import com.obsudim.mypsychologist.core.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

suspend fun <T> Flow<Resource<T>>.collectRequest(
    state: MutableStateFlow<BaseStateUI<T>>
){
    this.collect{resource->
        when(resource){
            is Resource.Error -> state.value = BaseStateUI.Error(resource.msg.toString())
            Resource.Loading -> state.value = BaseStateUI.Loading()
            is Resource.Success -> state.value = BaseStateUI.Content(resource.data)
        }
    }
}