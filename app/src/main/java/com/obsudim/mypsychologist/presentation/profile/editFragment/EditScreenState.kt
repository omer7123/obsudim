package com.obsudim.mypsychologist.presentation.profile.editFragment

import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.domain.entity.TagEntity
import com.obsudim.mypsychologist.ui.core.delegateItems.DelegateItem

sealed interface EditScreenState {
    data object Init : EditScreenState
    class CurrentData(val list: List<DelegateItem>, val birthday: String, val requests: List<TagEntity>) :
        EditScreenState
    class ValidationError(val listWithErrors: List<DelegateItem>): EditScreenState
    class Response(val result: Resource<String>) : EditScreenState
    data object Loading: EditScreenState

    class Error(val msg: String): EditScreenState

}