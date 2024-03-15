package com.example.mypsychologist.presentation.diagnostics

import com.example.mypsychologist.domain.entity.MBIResultEntity
import com.example.mypsychologist.domain.entity.TestQuestionEntity

sealed interface MBIScreenState {
    class Question(val answerVariants: TestQuestionEntity, val number: Int, val count: Int) :
        MBIScreenState

    class Result(val result: MBIResultEntity) : MBIScreenState

    object Error : MBIScreenState
}