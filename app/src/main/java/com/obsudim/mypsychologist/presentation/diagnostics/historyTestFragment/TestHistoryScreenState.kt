package com.obsudim.mypsychologist.presentation.diagnostics.historyTestFragment

import com.obsudim.mypsychologist.domain.entity.diagnosticEntity.TestResultsGetEntity

sealed interface TestHistoryScreenState {
    data object Init: TestHistoryScreenState
    data object Error: TestHistoryScreenState
    data object Loading: TestHistoryScreenState
    data class Data(val results: List<TestResultsGetEntity>, val checkedTests: Set<TestResultsGetEntity>):
        TestHistoryScreenState
}