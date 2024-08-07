package com.example.mypsychologist.presentation.diagnostics

import com.example.mypsychologist.domain.entity.diagnosticEntity.TestResultsGetEntity

sealed interface TestHistoryScreenState {
    data object Init: TestHistoryScreenState
    data object Error: TestHistoryScreenState
    data object Loading: TestHistoryScreenState
    class Data(val results: List<TestResultsGetEntity>): TestHistoryScreenState
}