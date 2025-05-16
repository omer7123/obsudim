package com.example.mypsychologist.presentation.main.mainFragment

import android.text.format.DateFormat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.R
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.useCase.exerciseUseCases.GetAllDailyExercisesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getAllDailyExerciseEntity: GetAllDailyExercisesUseCase

) : ViewModel() {
    private val _screenState: MutableStateFlow<MainScreenState> =
        MutableStateFlow(MainScreenState.Initial)
    val screenState: StateFlow<MainScreenState>
        get() = _screenState.asStateFlow()

    fun getInitialData(){
        var formattedDate: String = DateFormat.format("EEEE, d MMMM", Date()).toString()
        val formattedDateSplit = formattedDate.split(" ").toMutableList()
        formattedDateSplit[2] = formattedDateSplit[2].lowercase()
        formattedDate = formattedDateSplit.joinToString(" ")
        formattedDate = formattedDate.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
        }

        viewModelScope.launch {
            getAllDailyExerciseEntity().collect{tasksResult->
                when(tasksResult){
                    is Resource.Error -> _screenState.value = MainScreenState.Error(R.string.unknown_error_placeholder)
                    Resource.Loading -> _screenState.value = MainScreenState.Loading
                    is Resource.Success -> _screenState.value = MainScreenState.Content(tasksResult.data, formattedDate)
                }
            }
        }
    }

    class Factory @Inject constructor(
        private val getAllDailyExerciseEntity: GetAllDailyExercisesUseCase
    ) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(
                getAllDailyExerciseEntity,
            ) as T
        }
    }
}