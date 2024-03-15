package com.example.mypsychologist.presentation.diagnostics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.domain.entity.MBIResultEntity
import com.example.mypsychologist.domain.useCase.GetMBITestUseCase
import com.example.mypsychologist.domain.useCase.MBIConclusionUseCase
import com.example.mypsychologist.domain.useCase.SaveMBIResultUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MBITestViewModel(
    getMBITestUseCase: GetMBITestUseCase,
    private val mbiConclusionUseCase: MBIConclusionUseCase,
    private val saveMBIResultUseCase: SaveMBIResultUseCase

) : ViewModel() {
    private val questions = getMBITestUseCase()
    private val answers = MutableList(questions.size) { 0 }
    private var questionNumber = 0

    private val _screenState: MutableStateFlow<MBIScreenState> =
        MutableStateFlow(
            MBIScreenState.Question(
                questions[questionNumber],
                questionNumber,
                questions.size
            )
        )

    val screenState: StateFlow<MBIScreenState>
        get() = _screenState

    fun saveAnswerAndGoToNext(score: Int) {
        if (questionNumber == 5)
            answers[questionNumber] = 6 - score
        else
            answers[questionNumber] = score
        nextQuestion()
    }

    fun save(result: MBIResultEntity) {
        if (!saveMBIResultUseCase(result))
            _screenState.value = MBIScreenState.Error
    }

    private fun nextQuestion() {
        questionNumber += 1

        viewModelScope.launch {

            _screenState.value =
                if (questionNumber < questions.size) {
                    MBIScreenState.Question(
                        questions[questionNumber],
                        questionNumber,
                        questions.size
                    )
                } else {
                    MBIScreenState.Result(mbiConclusionUseCase(answers))
                }
        }
    }

    fun previousQuestion() {
        if (questionNumber > 0) {
            questionNumber -= 1

            viewModelScope.launch {
                _screenState.value =
                    MBIScreenState.Question(
                        questions[questionNumber],
                        questionNumber,
                        questions.size
                    )
            }
        }
    }

    class Factory @Inject constructor(
        private val getMBITestUseCase: GetMBITestUseCase,
        private val mbiConclusionUseCase: MBIConclusionUseCase,
        private val saveMBIResultUseCase: SaveMBIResultUseCase
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return MBITestViewModel(
                getMBITestUseCase, mbiConclusionUseCase, saveMBIResultUseCase
            ) as T
        }
    }
}