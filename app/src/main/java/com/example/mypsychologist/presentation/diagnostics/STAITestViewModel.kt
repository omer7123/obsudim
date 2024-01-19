package com.example.mypsychologist.presentation.diagnostics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.domain.entity.DASSResultEntity
import com.example.mypsychologist.domain.entity.STAIResultEntity
import com.example.mypsychologist.domain.useCase.DASSConclusionUseCase
import com.example.mypsychologist.domain.useCase.GetDASSTestUseCase
import com.example.mypsychologist.domain.useCase.GetSTAITestUseCase
import com.example.mypsychologist.domain.useCase.STAIConclusionUseCase
import com.example.mypsychologist.domain.useCase.SaveDASSResultUseCase
import com.example.mypsychologist.domain.useCase.SaveSTAIResultUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class STAITestViewModel(
    getSTAITestUseCase: GetSTAITestUseCase,
    private val staiConclusionUseCase: STAIConclusionUseCase,
    private val saveSTAIResultUseCase: SaveSTAIResultUseCase
) : ViewModel() {

    private val questions = getSTAITestUseCase()

    private val answers = MutableList(questions.size) { 0 }

    private var questionNumber = 0

    private val _screenState: MutableStateFlow<STAIScreenState> =
        MutableStateFlow(STAIScreenState.Question(
            questions[questionNumber],
            questionNumber,
            questions.size
        ))
    val screenState: StateFlow<STAIScreenState>
        get() = _screenState.asStateFlow()

    fun saveAnswerAndGoToNext(score: Int) {
        answers[questionNumber] = score

        nextQuestion()
    }

    fun save(result: STAIResultEntity) {
        if (!saveSTAIResultUseCase(result))
            _screenState.value = STAIScreenState.Error
    }

    private fun nextQuestion() {
        questionNumber += 1

        viewModelScope.launch {

            _screenState.value =
                if (questionNumber < questions.size) {
                    STAIScreenState.Question(
                        questions[questionNumber],
                        questionNumber,
                        questions.size
                    )
                } else {
                    STAIScreenState.Result(staiConclusionUseCase(answers))
                }
        }
    }

    fun previousQuestion() {
        if (questionNumber > 0) {
            questionNumber -= 1

            viewModelScope.launch {
                _screenState.value =
                    STAIScreenState.Question(
                        questions[questionNumber],
                        questionNumber,
                        questions.size
                    )
            }
        }
    }

    class Factory @Inject constructor(
        private val getSTAITestUseCase: GetSTAITestUseCase,
        private val staiConclusionUseCase: STAIConclusionUseCase,
        private val saveSTAIResultUseCase: SaveSTAIResultUseCase
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return STAITestViewModel(
                getSTAITestUseCase, staiConclusionUseCase, saveSTAIResultUseCase
            ) as T
        }
    }
}