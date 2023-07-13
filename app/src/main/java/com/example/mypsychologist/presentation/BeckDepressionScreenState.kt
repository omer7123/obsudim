package com.example.mypsychologist.presentation

import com.example.mypsychologist.domain.entity.TestQuestionEntity

sealed interface BeckDepressionScreenState {
    class Question(val answerVariants: TestQuestionEntity, val number: Int) :
        BeckDepressionScreenState

    class Result(val score: Int, val conclusionId: Int) : BeckDepressionScreenState
}