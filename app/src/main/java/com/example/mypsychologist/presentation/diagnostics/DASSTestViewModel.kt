package com.example.mypsychologist.presentation.diagnostics

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.domain.entity.DASSResultEntity
import com.example.mypsychologist.domain.useCase.CMQConclusionUseCase
import com.example.mypsychologist.domain.useCase.DASSConclusionUseCase
import com.example.mypsychologist.domain.useCase.GetCMQTestUseCase
import com.example.mypsychologist.domain.useCase.GetDASSTestUseCase
import com.example.mypsychologist.domain.useCase.SaveCMQResultUseCase
import com.example.mypsychologist.domain.useCase.SaveDASSResultUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class DASSTestViewModel(
    getDASSTestUseCase: GetDASSTestUseCase,
    private val DASSConclusionUseCase: DASSConclusionUseCase,
    private val saveDASSResultUseCase: SaveDASSResultUseCase
) : ViewModel() {

    private val questions = getDASSTestUseCase()

    private val answers = MutableList(questions.size) { 0 }

    private var questionNumber = 0

    private val _screenState: MutableStateFlow<DASSScreenState> =
        MutableStateFlow(
            DASSScreenState.Question(
                questions[questionNumber],
                questionNumber,
                questions.size
            )
        )
    val screenState: StateFlow<DASSScreenState>
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
                    DASSScreenState.Question(
                        questions[questionNumber],
                        questionNumber,
                        questions.size
                    )
                } else {
                    DASSScreenState.Result(DASSConclusionUseCase(answers))
                }
        }
    }

    fun previousQuestion() {
        if (questionNumber > 0) {
            questionNumber -= 1

            viewModelScope.launch {
                _screenState.value =
                    DASSScreenState.Question(
                        questions[questionNumber],
                        questionNumber,
                        questions.size
                    )
            }
        }
    }

    fun save(result: DASSResultEntity) {
        if (!saveDASSResultUseCase(result))
            _screenState.value = DASSScreenState.Error
    }

    class Factory @Inject constructor(
        private val getDASSTestUseCase: GetDASSTestUseCase,
        private val DASSConclusionUseCase: DASSConclusionUseCase,
        private val saveDASSResultUseCase: SaveDASSResultUseCase
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return DASSTestViewModel(
                getDASSTestUseCase, DASSConclusionUseCase, saveDASSResultUseCase
            ) as T
        }
    }
}