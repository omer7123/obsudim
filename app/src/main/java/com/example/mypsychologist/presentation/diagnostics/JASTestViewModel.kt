package com.example.mypsychologist.presentation.diagnostics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.domain.entity.JASResultEntity
import com.example.mypsychologist.domain.useCase.GetJASTestUseCase
import com.example.mypsychologist.domain.useCase.JASConclusionUseCase
import com.example.mypsychologist.domain.useCase.SaveJASResultUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class JASTestViewModel(
    getJASTestUseCase: GetJASTestUseCase,
    private val jasConclusionUseCase: JASConclusionUseCase,
    private val saveJASResultUseCase: SaveJASResultUseCase
) : ViewModel() {

    private val questions = getJASTestUseCase()
    private val answers = MutableList(questions.size) { 0 }
    private var questionNumber = 0

    private val _screenState: MutableStateFlow<JASScreenState> =
        MutableStateFlow(
            JASScreenState.Question(
                questions[questionNumber],
                questionNumber,
                questions.size
            )
        )

    val screenState: StateFlow<JASScreenState>
        get() = _screenState

    fun saveAnswerAndGoToNext(score: Int) {
        answers[questionNumber] = score

        nextQuestion()
    }

    fun save(result: JASResultEntity) {
        if (!saveJASResultUseCase(result))
            _screenState.value = JASScreenState.Error
    }

    private fun nextQuestion() {
        questionNumber += 1

        viewModelScope.launch {

            _screenState.value =
                if (questionNumber < questions.size) {
                    JASScreenState.Question(
                        questions[questionNumber],
                        questionNumber,
                        questions.size
                    )
                } else {
                    JASScreenState.Result(jasConclusionUseCase(answers))
                }
        }
    }

    fun previousQuestion() {
        if (questionNumber > 0) {
            questionNumber -= 1

            viewModelScope.launch {
                _screenState.value =
                    JASScreenState.Question(
                        questions[questionNumber],
                        questionNumber,
                        questions.size
                    )
            }
        }
    }

    class Factory @Inject constructor(
        private val getJASTestUseCase: GetJASTestUseCase,
        private val jasConclusionUseCase: JASConclusionUseCase,
        private val saveJASResultUseCase: SaveJASResultUseCase
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return JASTestViewModel(
                getJASTestUseCase, jasConclusionUseCase, saveJASResultUseCase
            ) as T
        }
    }
}