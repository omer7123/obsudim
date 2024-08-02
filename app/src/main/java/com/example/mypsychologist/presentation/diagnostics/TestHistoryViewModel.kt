package com.example.mypsychologist.presentation.diagnostics

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.diagnosticEntity.ScaleResultEntity
import com.example.mypsychologist.domain.entity.diagnosticEntity.ScaleResultWithTitileEntity
import com.example.mypsychologist.domain.entity.diagnosticEntity.TestInfoEntity
import com.example.mypsychologist.domain.entity.diagnosticEntity.TestResultsGetEntity
import com.example.mypsychologist.domain.entity.diagnosticEntity.TestResultsScalesWithTitleEntity
import com.example.mypsychologist.domain.useCase.retrofitUseCase.diagnosticsUseCases.GetTestInfoUseCase
import com.example.mypsychologist.domain.useCase.retrofitUseCase.diagnosticsUseCases.GetTestResultsUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class TestHistoryViewModel(
    private val getTestResultsUseCase: GetTestResultsUseCase,
    private val getTestInfoUseCase: GetTestInfoUseCase,
) : ViewModel() {

    private val _screenState: MutableStateFlow<TestHistoryScreenState> =
        MutableStateFlow(TestHistoryScreenState.Init)
    val screenState: StateFlow<TestHistoryScreenState>
        get() = _screenState.asStateFlow()

    fun loadHistory(testId: String) {
        _screenState.value = TestHistoryScreenState.Loading

        viewModelScope.launch {
            val testInfoDeferred = async { getTestInfoUseCase(testId) }
            val testResultsDeferred = async { getTestResultsUseCase(testId) }

            val testInfoRequest = testInfoDeferred.await()
            val testResultsRequest = testResultsDeferred.await()

            when {
                testInfoRequest is Resource.Error || testResultsRequest is Resource.Error -> _screenState.value =
                    TestHistoryScreenState.Error

                testInfoRequest is Resource.Loading && testResultsRequest is Resource.Loading -> _screenState.value =
                    TestHistoryScreenState.Loading

                testInfoRequest is Resource.Success && testResultsRequest is Resource.Success -> calculateFinalResult(
                    testInfoRequest.data,
                    testResultsRequest.data
                )
            }
        }
    }

    private fun calculateFinalResult(
        infoTest: TestInfoEntity,
        resultsOfTest: List<TestResultsGetEntity>
    ) {
        val result = resultsOfTest.map { it.toTestResultWithTitle(infoTest) }
        Log.e("FInal res", result.toString())
        _screenState.value = TestHistoryScreenState.Data(result)
    }

    private fun TestResultsGetEntity.toTestResultWithTitle(
        infoTest: TestInfoEntity
    ): TestResultsScalesWithTitleEntity {
        return TestResultsScalesWithTitleEntity(
            testId,
            testResultId,
            datetime,
            scaleResults.map { it.toScalesWithTitle(infoTest) })
    }

    private fun ScaleResultEntity.toScalesWithTitle(infoTest: TestInfoEntity): ScaleResultWithTitileEntity {
        for (scaleTest in infoTest.scales) {
            if (scaleId == scaleTest.scaleId) {
                return ScaleResultWithTitileEntity(scaleTest.title, scaleId, score)
            }
        }
        return ScaleResultWithTitileEntity("", scaleId, score)
    }

    class Factory @Inject constructor(
        private val getTestResultsUseCase: GetTestResultsUseCase,
        private val getTestInfoUseCase: GetTestInfoUseCase,

        ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return TestHistoryViewModel(
                getTestResultsUseCase, getTestInfoUseCase
            ) as T
        }
    }
}