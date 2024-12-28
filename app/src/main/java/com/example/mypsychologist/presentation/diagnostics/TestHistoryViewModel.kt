package com.example.mypsychologist.presentation.diagnostics

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.useCase.diagnosticsUseCases.GetTestResultsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class TestHistoryViewModel(
    private val getTestResultsUseCase: GetTestResultsUseCase,

    ) : ViewModel() {

    private val _screenState: MutableStateFlow<TestHistoryScreenState> =
        MutableStateFlow(TestHistoryScreenState.Init)
    val screenState: StateFlow<TestHistoryScreenState>
        get() = _screenState.asStateFlow()

    fun loadHistory(testId: String) {
        _screenState.value = TestHistoryScreenState.Loading

        viewModelScope.launch {
            when (val res = getTestResultsUseCase(testId)) {
                is Resource.Error -> {
                    Log.e("Error TestHit", res.msg.toString())
                    _screenState.value = TestHistoryScreenState.Error
                }
                Resource.Loading -> _screenState.value = TestHistoryScreenState.Loading
                is Resource.Success -> _screenState.value =
                    TestHistoryScreenState.Data(res.data, emptySet())
            }
        }
    }

    fun getResultTestById(testResultId: String, checked: Boolean) {

        viewModelScope.launch(Dispatchers.IO) {
            if (_screenState.value is TestHistoryScreenState.Data) {
                val currentState = _screenState.value as TestHistoryScreenState.Data

                val allResults = currentState.results
                val checkedTests = currentState.checkedTests.toMutableSet()

                val selectedTest = allResults.find { it.testResultId == testResultId }

                if (checked) {
                    checkedTests.add(selectedTest!!)
                } else {
                    checkedTests.remove(selectedTest)
                }

                _screenState.value = currentState.copy(checkedTests = checkedTests)
            }
        }
    }

    class Factory @Inject constructor(
        private val getTestResultsUseCase: GetTestResultsUseCase,

        ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return TestHistoryViewModel(
                getTestResultsUseCase
            ) as T
        }
    }
}