package com.example.mypsychologist.presentation.exercises

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.domain.useCase.GetProblemAnalysisUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class BeliefsVerificationViewModel(private val getProblemAnalysisUseCase: GetProblemAnalysisUseCase) : ViewModel() {
    private val _screenState: MutableStateFlow<BeliefsScreenState> =
        MutableStateFlow(BeliefsScreenState.Init)
    val screenState: StateFlow<BeliefsScreenState>
        get() = _screenState.asStateFlow()

    init {
        _screenState.value = BeliefsScreenState.Loading
        viewModelScope.launch {
            _screenState.value = BeliefsScreenState.Data(getProblemAnalysisUseCase())
        }
    }

    class Factory @Inject constructor(
        private val getProblemAnalysisUseCase: GetProblemAnalysisUseCase
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return BeliefsVerificationViewModel(
                getProblemAnalysisUseCase,
            ) as T
        }
    }
}