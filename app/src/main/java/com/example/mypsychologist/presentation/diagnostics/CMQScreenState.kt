package com.example.mypsychologist.presentation.diagnostics

import com.example.mypsychologist.domain.entity.ResultWithScalesEntity
import com.example.mypsychologist.domain.entity.TestQuestionEntity

sealed interface CMQScreenState {
    class Question(val answerVariants: TestQuestionEntity, val number: Int, val count: Int) :
        CMQScreenState

    class Result(val result: ResultWithScalesEntity) : CMQScreenState

    object Error : CMQScreenState
}