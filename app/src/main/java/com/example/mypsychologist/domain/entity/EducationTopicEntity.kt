package com.example.mypsychologist.domain.entity

import com.example.mypsychologist.domain.useCase.GetEducationMaterialUseCase

data class EducationTopicEntity (
    val titleId: Int,
    val cardCount: Int,
    val tag: GetEducationMaterialUseCase.Topic,
    val currentCard: Int = 0
)
