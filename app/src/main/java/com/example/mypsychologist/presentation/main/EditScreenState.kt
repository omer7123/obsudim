package com.example.mypsychologist.presentation.main

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.ClientInfoEntity
import com.example.mypsychologist.domain.entity.TagEntity
import com.example.mypsychologist.ui.DelegateItem

sealed interface EditScreenState {
    data object Init : EditScreenState
    class CurrentData(val list: List<DelegateItem>, val birthday: String, val requests: List<TagEntity>) : EditScreenState
    class ValidationError(val listWithErrors: List<DelegateItem>): EditScreenState
    class Response(val result: Resource<String>) : EditScreenState
    data object Loading: EditScreenState

    class Error(val msg: String): EditScreenState

}