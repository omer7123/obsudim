package com.obsudim.mypsychologist.presentation.diagnostics.hostTestFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.domain.entity.diagnosticEntity.TestHostEntity
import com.obsudim.mypsychologist.domain.useCase.diagnosticsUseCases.GetTestHostInfoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class HostTestViewModel @Inject constructor(
    private val getTestHostInfoUseCase: GetTestHostInfoUseCase
) : ViewModel() {
    private val _screenState: MutableStateFlow<HostTestScreenState> = MutableStateFlow(
        HostTestScreenState.Initial
    )
    val screenState: StateFlow<HostTestScreenState> = _screenState.asStateFlow()

    fun initData(testId: String) {
        viewModelScope.launch {
            getTestHostInfoUseCase(testId).collect { resource ->
                when (resource) {
                    is Resource.Error<TestHostEntity> -> _screenState.value =
                        HostTestScreenState.Error

                    Resource.Loading -> _screenState.value = HostTestScreenState.Loading
                    is Resource.Success<TestHostEntity> -> _screenState.value =
                        HostTestScreenState.Content(resource.data)
                }
            }
        }
    }
}