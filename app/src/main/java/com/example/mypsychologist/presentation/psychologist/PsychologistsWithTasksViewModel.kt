package com.example.mypsychologist.presentation.psychologist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.domain.useCase.GetOwnPsychologistsUseCase
import com.example.mypsychologist.domain.useCase.GetTasksUseCase
import com.example.mypsychologist.domain.useCase.MarkTaskUseCase
import com.example.mypsychologist.presentation.ListScreenState
import com.example.mypsychologist.ui.DelegateItem
import com.example.mypsychologist.ui.psychologist.OwnPsychologistDelegateItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class PsychologistsWithTasksViewModel(
    private val getOwnPsychologistsUseCase: GetOwnPsychologistsUseCase,
    private val getTasksUseCase: GetTasksUseCase,
    private val markTaskUseCase: MarkTaskUseCase
) : ViewModel() {
    private val _screenState: MutableStateFlow<ListScreenState> =
        MutableStateFlow(ListScreenState.Init)
    val screenState: StateFlow<ListScreenState>
        get() = _screenState.asStateFlow()

    init {
        _screenState.value = ListScreenState.Loading

        viewModelScope.launch(Dispatchers.IO) {
//            _screenState.value = ListScreenState.Data(
//                getOwnPsychologistsUseCase().toDelegateItem().concatenate()
//            )
        }
    }

    fun getPsych() {
        viewModelScope.launch {
            val res = getOwnPsychologistsUseCase()

        }
    }

    private suspend fun List<OwnPsychologistDelegateItem>.concatenate(): List<DelegateItem> {
        val psychologistsWithTasks = mutableListOf<DelegateItem>()

        forEach { psychologist ->

            psychologistsWithTasks.add(psychologist)
//            psychologistsWithTasks.addAll(getTasksUseCase(psychologist.content().first).toDelegateItem())
        }

        return psychologistsWithTasks
    }

    fun markTask(taskId: String, psychologistId: String, isChecked: Boolean) {
//        markTaskUseCase(taskId, psychologistId, isChecked)
    }

    class Factory @Inject constructor(
        private val getOwnPsychologistsUseCase: GetOwnPsychologistsUseCase,
        private val getTasksUseCase: GetTasksUseCase,
        private val markTaskUseCase: MarkTaskUseCase
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return PsychologistsWithTasksViewModel(
                getOwnPsychologistsUseCase,
                getTasksUseCase,
                markTaskUseCase
            ) as T
        }
    }
}