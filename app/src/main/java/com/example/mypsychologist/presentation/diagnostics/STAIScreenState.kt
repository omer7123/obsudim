package com.example.mypsychologist.presentation.diagnostics

import com.example.mypsychologist.domain.entity.STAIResultEntity
import com.example.mypsychologist.domain.entity.TestQuestionEntity

sealed interface STAIScreenState {
    class Question(val answerVariants: TestQuestionEntity, val number: Int, val count: Int) :
        STAIScreenState

    class Result(val result: STAIResultEntity) : STAIScreenState

    data object Error : STAIScreenState
}