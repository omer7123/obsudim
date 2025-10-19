package com.obsudim.mypsychologist.presentation.diagnostics.testsFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.domain.useCase.diagnosticsUseCases.GetAllTestsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class TestsViewModel(
    private val getAllTestsUseCase: GetAllTestsUseCase
) : ViewModel() {

    private val _screenState: MutableStateFlow<TestsScreenState> =
        MutableStateFlow(TestsScreenState.Initial)

    val screenState: StateFlow<TestsScreenState> = _screenState.asStateFlow()

    fun getTests() {
        _screenState.value = TestsScreenState.Loading
        viewModelScope.launch {
            when (val result = getAllTestsUseCase()) {
                is Resource.Success -> {
                    _screenState.value = TestsScreenState.Content(
                        result.data
                    )
                }

                is Resource.Error -> _screenState.value =
                    TestsScreenState.Error(result.msg.toString())

                Resource.Loading -> {}
            }
        }
    }

    class Factory @Inject constructor(
        private val getAllTestsUseCase: GetAllTestsUseCase
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST") return TestsViewModel(getAllTestsUseCase) as T
        }
    }
}