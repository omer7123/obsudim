package com.example.mypsychologist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.TagEntity
import com.example.mypsychologist.domain.useCase.GetTagsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class TagsViewModel(private val getTagsUseCase: GetTagsUseCase) : ViewModel() {

    private val _screenState: MutableStateFlow<TagsScreenState> =
        MutableStateFlow(TagsScreenState.Init)
    val screenState: StateFlow<TagsScreenState>
        get() = _screenState.asStateFlow()

    init {
        getTags()
    }

    private fun getTags() {
        _screenState.value = TagsScreenState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            _screenState.value = renderResponse(getTagsUseCase())
        }
    }

    private fun renderResponse(it: Resource<List<TagEntity>>): TagsScreenState =
        when (it) {
            is Resource.Error -> TagsScreenState.Error
            is Resource.Loading -> TagsScreenState.Loading
            is Resource.Success -> TagsScreenState.Data(it.data)
        }

    class Factory @Inject constructor(
        private val getTagsUseCase: GetTagsUseCase
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return TagsViewModel(getTagsUseCase) as T
        }
    }
}