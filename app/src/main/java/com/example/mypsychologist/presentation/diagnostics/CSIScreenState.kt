package com.example.mypsychologist.presentation.diagnostics

import com.example.mypsychologist.domain.entity.CSIResultEntity
import com.example.mypsychologist.domain.entity.TestQuestionEntity

sealed interface CSIScreenState {
    class Question(val answerVariants: TestQuestionEntity, val number: Int, val count: Int) :
        CSIScreenState

    class Result(val result: CSIResultEntity) : CSIScreenState

    object Error : CSIScreenState
}