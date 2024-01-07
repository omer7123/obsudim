package com.example.mypsychologist.presentation.diagnostics

import com.example.mypsychologist.domain.entity.DASSResultEntity
import com.example.mypsychologist.domain.entity.TestQuestionEntity

sealed interface DASSScreenState {
    class Question(val answerVariants: TestQuestionEntity, val number: Int, val count: Int) :
        DASSScreenState

    class Result(val result: DASSResultEntity) : DASSScreenState

    object Error : DASSScreenState
}