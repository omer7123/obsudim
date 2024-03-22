package com.example.mypsychologist.presentation.diagnostics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.domain.entity.CSIResultEntity
import com.example.mypsychologist.domain.entity.MBIResultEntity
import com.example.mypsychologist.domain.useCase.CSITestConclusionUseCase
import com.example.mypsychologist.domain.useCase.GetCSITestUseCase
import com.example.mypsychologist.domain.useCase.GetMBITestUseCase
import com.example.mypsychologist.domain.useCase.MBIConclusionUseCase
import com.example.mypsychologist.domain.useCase.SaveCSIResultUseCase
import com.example.mypsychologist.domain.useCase.SaveMBIResultUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CSITestViewModel(
    getCSITestUseCase: GetCSITestUseCase,
    private val csiConclusionUseCase: CSITestConclusionUseCase,
    private val saveCSIResultUseCase: SaveCSIResultUseCase
): ViewModel() {
    private val questions = getCSITestUseCase()
    private val answers = MutableList(questions.size) { 0 }
    private var questionNumber = 0

    private val _screenState: MutableStateFlow<CSIScreenState> =
        MutableStateFlow(
            CSIScreenState.Question(
                questions[questionNumber],
                questionNumber,
                questions.size
            )
        )

    val screenState: StateFlow<CSIScreenState>
        get() = _screenState

    fun saveAnswerAndGoToNext(score: Int) {
        if (questionNumber == 5)
            answers[questionNumber] = 6 - score
        else
            answers[questionNumber] = score
        nextQuestion()
    }

    fun save(result: CSIResultEntity) {
        if (!saveCSIResultUseCase(result))
            _screenState.value = CSIScreenState.Error
    }

    private fun nextQuestion() {
        questionNumber += 1

        viewModelScope.launch {

            _screenState.value =
                if (questionNumber < questions.size) {
                    CSIScreenState.Question(
                        questions[questionNumber],
                        questionNumber,
                        questions.size
                    )
                } else {
                    CSIScreenState.Result(csiConclusionUseCase(answers))
                }
        }
    }

    fun previousQuestion() {
        if (questionNumber > 0) {
            questionNumber -= 1

            viewModelScope.launch {
                _screenState.value =
                    CSIScreenState.Question(
                        questions[questionNumber],
                        questionNumber,
                        questions.size
                    )
            }
        }
    }

    class Factory @Inject constructor(
        private val getCSITestUseCase: GetCSITestUseCase,
        private val csiConclusionUseCase: CSITestConclusionUseCase,
        private val saveCSIResultUseCase: SaveCSIResultUseCase
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return CSITestViewModel(
                getCSITestUseCase, csiConclusionUseCase, saveCSIResultUseCase
            ) as T
        }
    }
}