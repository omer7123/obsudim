package com.example.mypsychologist.presentation.diagnostics

import com.example.mypsychologist.domain.entity.JASResultEntity
import com.example.mypsychologist.domain.entity.TestQuestionEntity

sealed interface JASScreenState {
    class Question(val answerVariants: TestQuestionEntity, val number: Int, val count: Int) :
        JASScreenState

    class Result(val result: JASResultEntity) : JASScreenState

    object Error : JASScreenState

}