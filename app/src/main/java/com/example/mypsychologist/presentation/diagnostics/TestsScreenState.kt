package com.example.mypsychologist.presentation.diagnostics

import com.example.mypsychologist.domain.entity.diagnosticEntity.TestEntity

sealed class TestsScreenState {
    data object Initial: TestsScreenState()
    data object Loading: TestsScreenState()
    data class Error(val msg:String): TestsScreenState()
    data class Content(val data: List<TestEntity>): TestsScreenState()
}