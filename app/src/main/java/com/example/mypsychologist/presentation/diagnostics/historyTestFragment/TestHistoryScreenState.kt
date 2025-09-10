package com.example.mypsychologist.presentation.diagnostics.historyTestFragment

import com.example.mypsychologist.domain.entity.diagnosticEntity.TestResultsGetEntity

sealed interface TestHistoryScreenState {
    data object Init: TestHistoryScreenState
    data object Error: TestHistoryScreenState
    data object Loading: TestHistoryScreenState
    data class Data(val results: List<TestResultsGetEntity>, val checkedTests: Set<TestResultsGetEntity>):
        TestHistoryScreenState
}