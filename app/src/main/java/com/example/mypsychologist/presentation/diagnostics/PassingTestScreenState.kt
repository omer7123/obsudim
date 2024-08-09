package com.example.mypsychologist.presentation.diagnostics

import com.example.mypsychologist.domain.entity.diagnosticEntity.QuestionOfTestEntity
import com.example.mypsychologist.domain.entity.diagnosticEntity.ResultAfterSaveEntity

sealed class PassingTestScreenState {
    data object Initial : PassingTestScreenState()
    data object Loading : PassingTestScreenState()
    data class Result(val result: ResultAfterSaveEntity) : PassingTestScreenState()

    data class Question(
        val answerVariants: QuestionOfTestEntity,
        val number: Int,
        val count: Int
    ) :
        PassingTestScreenState()

    data class Error(val msg: String) : PassingTestScreenState()
}
