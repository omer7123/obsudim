package com.example.mypsychologist.presentation.diagnostics.passingTestFragment

import com.example.mypsychologist.domain.entity.diagnosticEntity.QuestionOfTestEntity
import com.example.mypsychologist.domain.entity.diagnosticEntity.ResultAfterSaveEntity

sealed class PassingTestScreenState {
    data object Initial : PassingTestScreenState()
    data object Loading : PassingTestScreenState()
    data class Result(val result: ResultAfterSaveEntity) : PassingTestScreenState()

    data class Questions(val list: List<QuestionOfTestEntity>) : PassingTestScreenState()

    data class Question(val position: Int) : PassingTestScreenState()

    data class Content(val title: String, val desc: String) : PassingTestScreenState()
    data class Error(val msg: String) : PassingTestScreenState()
}
