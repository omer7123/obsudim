package com.example.mypsychologist.presentation.diagnostics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.useCase.retrofitUseCase.diagnosticsUseCases.GetTestResultsUseCase
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
                is Resource.Error -> _screenState.value = TestHistoryScreenState.Error
                Resource.Loading -> _screenState.value = TestHistoryScreenState.Loading
                is Resource.Success -> _screenState.value = TestHistoryScreenState.Data(res.data, emptyList())
            }
        }
    }

    fun getResultTestById(testResultId: String, checked: Boolean) {

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