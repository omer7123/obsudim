package com.example.mypsychologist.presentation.diagnostics

import com.example.mypsychologist.domain.entity.TestResultEntity

sealed interface TestHistoryScreenState {
    object Init: TestHistoryScreenState
    object Error: TestHistoryScreenState
    object Loading: TestHistoryScreenState
    class Data(val results: List<TestResultEntity>): TestHistoryScreenState
}