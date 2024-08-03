package com.example.mypsychologist.presentation.diagnostics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.diagnosticEntity.QuestionOfTestEntity
import com.example.mypsychologist.domain.useCase.retrofitUseCase.diagnosticsUseCases.GetQuestionsOfTestByIdUseCase
import com.example.mypsychologist.domain.useCase.retrofitUseCase.diagnosticsUseCases.SaveResultTestUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class PassingTestViewModel @Inject constructor(
    private val saveResultTestUseCase: SaveResultTestUseCase,
    private val getQuestionsOfTestByIdUseCase: GetQuestionsOfTestByIdUseCase,
) : ViewModel() {

    private val _screenState: MutableLiveData<PassingTestScreenState> = MutableLiveData()
    val screenState: LiveData<PassingTestScreenState> = _screenState

    private var _questions: List<QuestionOfTestEntity>? = null
    private val questions get() = requireNotNull(_questions)

    private var questionNumber = 0

    fun getQuestions(testId: String) {
        viewModelScope.launch {
            when (val questionsRequest = getQuestionsOfTestByIdUseCase(testId)) {
                is Resource.Error -> _screenState.value =
                    PassingTestScreenState.Error(questionsRequest.msg.toString())

                Resource.Loading -> _screenState.value = PassingTestScreenState.Loading
                is Resource.Success -> {
                    _questions = questionsRequest.data

                    _screenState.value = PassingTestScreenState.Question(
                        questions[questionNumber],
                        questions[questionNumber].number,
                        questions.size
                    )
                }
            }
        }
    }
}