package com.example.mypsychologist.presentation.diagnostics

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.useCase.retrofitUseCase.diagnosticsUseCases.GetTestInfoUseCase
import com.example.mypsychologist.domain.useCase.retrofitUseCase.diagnosticsUseCases.GetTestResultsUseCase
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

//    private val titleResourcesToDataBaseTitles = mapOf(
//        R.string.depression_beck_test to GetDepressionBeckTestQuestionsUseCase.TEST_NAME,
//        R.string.cmq to GetCMQTestUseCase.TEST_NAME
//    )


    fun loadHistory(testId: String) {
        _screenState.value = TestHistoryScreenState.Loading

        viewModelScope.launch {
            Log.e("sdasda", getTestInfoUseCase(testId).toString())
            when (val result = getTestResultsUseCase(testId)) {
                is Resource.Error -> _screenState.value = TestHistoryScreenState.Error
                Resource.Loading -> {}
                is Resource.Success -> _screenState.value = TestHistoryScreenState.Data(result.data)
            }
        }
//        viewModelScope.launch {
//            titleResourcesToDataBaseTitles[titleId]?.let { title ->
//                _screenState.value = if (clientId == OWN)
//                    TestHistoryScreenState.Data(getTestHistoryUseCase(title))
//                else
//                    TestHistoryScreenState.Data(getClientTestHistoryUseCase(clientId, title))
//            }
//        }
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