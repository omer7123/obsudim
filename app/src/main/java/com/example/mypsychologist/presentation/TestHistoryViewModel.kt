package com.example.mypsychologist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.R
import com.example.mypsychologist.domain.useCase.GetClientTestHistoryUseCase
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
    @Assisted private val clientId: String,
    private val getTestHistoryUseCase: GetTestHistoryUseCase,
    private val getClientTestHistoryUseCase: GetClientTestHistoryUseCase
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
                _screenState.value = if (clientId == OWN)
                     TestHistoryScreenState.Data(getTestHistoryUseCase(title))
                else
                    TestHistoryScreenState.Data(getClientTestHistoryUseCase(clientId, title))
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted title: Int,
            @Assisted clientId: String
        ): TestHistoryViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: Factory,
            title: Int, clientId: String
        ) = object : ViewModelProvider.Factory {

            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                assistedFactory.create(title, clientId) as T
        }

        const val OWN = "own"
    }
}