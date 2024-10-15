package com.example.mypsychologist.presentation.education

import com.example.mypsychologist.domain.entity.educationEntity.ItemMaterialEntity

sealed interface EducationScreenState {
    data object Initial: EducationScreenState
    data object Loading: EducationScreenState
    data class Error(val msg: String): EducationScreenState
    data class Content(val data: List<ItemMaterialEntity>): EducationScreenState
}