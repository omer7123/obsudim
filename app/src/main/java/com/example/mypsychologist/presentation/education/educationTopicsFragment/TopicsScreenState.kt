package com.example.mypsychologist.presentation.education.educationTopicsFragment

import com.example.mypsychologist.domain.entity.educationEntity.TopicEntity


sealed class TopicsScreenState {
    data object Initial: TopicsScreenState()
    data object Loading: TopicsScreenState()
    data class Error(val msg:String): TopicsScreenState()
    data class Content(val data: List<TopicEntity>): TopicsScreenState()
}