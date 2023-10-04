package com.example.mypsychologist.presentation.diagnostics

import com.example.mypsychologist.domain.entity.TestQuestionEntity

sealed interface BeckDepressionScreenState {
    class Question(val answerVariants: TestQuestionEntity, val number: Int, val count: Int) :
        BeckDepressionScreenState

    class Result(val score: Int, val conclusionId: Int) : BeckDepressionScreenState
    object Error: BeckDepressionScreenState
}