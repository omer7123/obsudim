package com.example.mypsychologist.presentation.education.educationFragment

import com.example.mypsychologist.domain.entity.educationEntity.EducationsEntity

sealed interface EducationScreenState {
    data object Initial: EducationScreenState
    data object Loading: EducationScreenState
    data class Error(val msg: String): EducationScreenState
    data class Content(val data: EducationsEntity): EducationScreenState
}