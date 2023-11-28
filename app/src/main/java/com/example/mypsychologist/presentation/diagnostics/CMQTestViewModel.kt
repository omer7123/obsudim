package com.example.mypsychologist.presentation.diagnostics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.domain.useCase.CMQConclusionUseCase
import com.example.mypsychologist.domain.useCase.GetCMQTestUseCase
import com.example.mypsychologist.domain.useCase.SaveCMQResultUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CMQTestViewModel(
    getSMQTestUseCase: GetCMQTestUseCase,
    private val CMQConclusionUseCase: CMQConclusionUseCase,
    private val saveCMQResultUseCase: SaveCMQResultUseCase
) : ViewModel() {

    private val questions = getSMQTestUseCase()

    private val answers = MutableList(questions.size) { 0 }

    private var questionNumber = 0

    private val _screenState: MutableStateFlow<CMQScreenState> =
        MutableStateFlow(
            CMQScreenState.Question(
                questions[questionNumber],
                questionNumber,
                questions.size
            )
        )
    val screenState: StateFlow<CMQScreenState>
        get() = _screenState.asStateFlow()

    fun saveAnswerAndGoToNext(score: Int) {
        answers[questionNumber] = score

        nextQuestion()
    }

    private fun nextQuestion() {
        questionNumber += 1

        viewModelScope.launch {

            _screenState.value =
                if (questionNumber < questions.size) {
                    CMQScreenState.Question(
                        questions[questionNumber],
                        questionNumber,
                        questions.size
                    )
                } else {
                    CMQScreenState.Result(
                        CMQConclusionUseCase(answers)
                    )
                }
        }
    }

    fun previousQuestion() {
        if (questionNumber > 0) {
            questionNumber -= 1

            viewModelScope.launch {
                _screenState.value =
                    CMQScreenState.Question(
                        questions[questionNumber],
                        questionNumber,
                        questions.size
                    )
            }
        }
    }

    fun saveResult(result: Int, conclusion: String) = run {
        if (!saveCMQResultUseCase(result, conclusion)) {
            _screenState.value = CMQScreenState.Error
        }
    }

    class Factory @Inject constructor(
        private val getSMQTestUseCase: GetCMQTestUseCase,
        private val CMQConclusionUseCase: CMQConclusionUseCase,
        private val saveCMQResultUseCase: SaveCMQResultUseCase
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return CMQTestViewModel(
                getSMQTestUseCase, CMQConclusionUseCase, saveCMQResultUseCase
            ) as T
        }
    }
}