package com.example.mypsychologist.presentation.exercises

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.DiaryRecordEntity
import com.example.mypsychologist.domain.useCase.GetClientThoughtDiariesUseCase
import com.example.mypsychologist.domain.useCase.GetThoughtDiariesUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ThoughtDiariesViewModel @AssistedInject constructor(
    private val getThoughtDiariesUseCase: GetThoughtDiariesUseCase,
    private val getClientThoughtDiariesUseCase: GetClientThoughtDiariesUseCase,
    @Assisted private val clientId: String
) :
    ViewModel() {

    private val _screenState: MutableStateFlow<Resource<List<DiaryRecordEntity>>> =
        MutableStateFlow(Resource.Loading)

    val screenState: StateFlow<Resource<List<DiaryRecordEntity>>>
        get() = _screenState.asStateFlow()

    fun loadDiaries() {
        _screenState.value = Resource.Loading

        viewModelScope.launch {
            _screenState.value = getThoughtDiariesUseCase()
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted clientId: String
        ): ThoughtDiariesViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: Factory,
            clientId: String
        ) = object : ViewModelProvider.Factory {

            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                assistedFactory.create(clientId) as T
        }

        const val OWN = "own"
    }
}