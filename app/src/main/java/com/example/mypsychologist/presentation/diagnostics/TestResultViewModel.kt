package com.example.mypsychologist.presentation.diagnostics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.diagnosticEntity.TestResultsGetEntity
import com.example.mypsychologist.domain.useCase.testUseCase.GetTestResultUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TestResultViewModel @AssistedInject constructor(
    private val getTestResultUseCase: GetTestResultUseCase,
    @Assisted(TEST_RESULT_ID) testResultId: String
) :
    ViewModel() {

    private val _screenState: MutableStateFlow<Resource<TestResultsGetEntity>> =
        MutableStateFlow(Resource.Loading)
    val screenState: StateFlow<Resource<TestResultsGetEntity>> =
        _screenState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            loadResult(testResultId)
        }
    }

    private suspend fun loadResult(testResultId: String) {
        _screenState.value = getTestResultUseCase(testResultId)
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted(TEST_RESULT_ID) type: String
        ): TestResultViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: Factory,
            testResultId: String
        ) = object : ViewModelProvider.Factory {

            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                assistedFactory.create(testResultId) as T
        }

        const val TEST_RESULT_ID = "testResultId"
    }

}