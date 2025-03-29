package com.example.mypsychologist.presentation.exercises.statementProblemsAndTargetFragment

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class StatementProblemsAndTargetViewModel @Inject constructor() : ViewModel() {

    private var _screenState: MutableStateFlow<StatementProblemsAndTargetScreenState> = MutableStateFlow(StatementProblemsAndTargetScreenState(
        listSphere = listOf("Работа", "Сфера2", "Сфера3")
        )
    )
    val screenState: StateFlow<StatementProblemsAndTargetScreenState> = _screenState


}