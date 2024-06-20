package com.example.mypsychologist.presentation

import com.example.mypsychologist.domain.entity.TagEntity


sealed interface TagsScreenState {
    data object Init: TagsScreenState
    data object Error: TagsScreenState
    data object Loading: TagsScreenState
    class Data(val items: List<TagEntity>): TagsScreenState
}