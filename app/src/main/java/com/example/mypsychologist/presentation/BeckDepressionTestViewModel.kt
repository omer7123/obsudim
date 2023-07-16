package com.example.mypsychologist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.domain.useCase.BeckDepressionTestConclusionUseCase
import com.example.mypsychologist.domain.useCase.GetDepressionBeckTestQuestionsUseCase
import com.example.mypsychologist.domain.useCase.SaveDepressionBeckResultUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class BeckDepressionTestViewModel(
    getDepressionBeckTestQuestionsUseCase: GetDepressionBeckTestQuestionsUseCase,
    private val beckDepressionTestConclusionUseCase: BeckDepressionTestConclusionUseCase,
    private val saveDepressionBeckResultUseCase: SaveDepressionBeckResultUseCase
) : ViewModel() {
    private val questions = getDepressionBeckTestQuestionsUseCase()

    private val answers = MutableList(questions.size) { 0 }

    private var questionNumber = 0

    private val _screenState: MutableStateFlow<BeckDepressionScreenState> =
        MutableStateFlow(
            BeckDepressionScreenState.Question(
                questions[questionNumber],
                questionNumber
            )
        )
    val screenState: StateFlow<BeckDepressionScreenState>
        get() =
            _screenState.asStateFlow()

    fun saveAnswerAndGoToNext(score: Int) {
        answers[questionNumber] = score

        nextQuestion()
    }

    private fun nextQuestion() {
        questionNumber += 1
        viewModelScope.launch {
            _screenState.emit(

                if (questionNumber < questions.size)
                    BeckDepressionScreenState.Question(questions[questionNumber], questionNumber)
                else {
                    val result = answers.sum()
                    saveAndGetScreenState(result, beckDepressionTestConclusionUseCase(result))
                }
            )

        }
    }

    private fun saveAndGetScreenState(result: Int, conclusionId: Int) = run {

        saveDepressionBeckResultUseCase(result, conclusionId)

        BeckDepressionScreenState.Result(
            result,
            conclusionId
        )
    }

    fun lastQuestion() {
        if (questionNumber > 0) {
            questionNumber -= 1

            viewModelScope.launch {
                _screenState.emit(
                    BeckDepressionScreenState.Question(
                        questions[questionNumber],
                        questionNumber
                    )
                )
            }
        }
    }

    class Factory @Inject constructor(
        private val getDepressionBeckTestQuestionsUseCase: GetDepressionBeckTestQuestionsUseCase,
        private val beckDepressionTestResultUseCase: BeckDepressionTestConclusionUseCase,
        private val saveDepressionBeckResultUseCase: SaveDepressionBeckResultUseCase
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return BeckDepressionTestViewModel(
                getDepressionBeckTestQuestionsUseCase,
                beckDepressionTestResultUseCase,
                saveDepressionBeckResultUseCase
            ) as T
        }
    }
}