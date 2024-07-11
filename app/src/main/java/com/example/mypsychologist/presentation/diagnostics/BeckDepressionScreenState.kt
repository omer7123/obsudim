package com.example.mypsychologist.presentation.diagnostics

import com.example.mypsychologist.domain.entity.BeckDepressionResultEntity
import com.example.mypsychologist.domain.entity.TestQuestionEntity

sealed interface BeckDepressionScreenState {
    class Question(val answerVariants: TestQuestionEntity, val number: Int, val count: Int) :
        BeckDepressionScreenState

    class Result(val result: BeckDepressionResultEntity) : BeckDepressionScreenState
    data object Error: BeckDepressionScreenState
}