package com.example.mypsychologist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.R
import com.example.mypsychologist.domain.useCase.GetDepressionBeckTestQuestionsUseCase
import com.example.mypsychologist.domain.useCase.GetTestHistoryUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TestHistoryViewModel @AssistedInject constructor(
    @Assisted private val titleId: Int,
    private val getTestHistoryUseCase: GetTestHistoryUseCase
) : ViewModel() {

    private val _screenState: MutableStateFlow<TestHistoryScreenState> =
        MutableStateFlow(TestHistoryScreenState.Init)
    val screenState: StateFlow<TestHistoryScreenState>
        get() = _screenState.asStateFlow()

    private val titleResourcesToDataBaseTitles = mapOf(
        R.string.depression_beck_test to GetDepressionBeckTestQuestionsUseCase.TEST_NAME
    )

    init {
        loadHistory()
    }

    private fun loadHistory() {
        _screenState.value = TestHistoryScreenState.Loading

        viewModelScope.launch {
            titleResourcesToDataBaseTitles[titleId]?.let { title ->
                _screenState.value = TestHistoryScreenState.Data(getTestHistoryUseCase(title))
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted title: Int
        ): TestHistoryViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: Factory,
            title: Int
        ) = object : ViewModelProvider.Factory {

            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                assistedFactory.create(title) as T
        }
    }
}