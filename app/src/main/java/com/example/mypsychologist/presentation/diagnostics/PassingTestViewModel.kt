package com.example.mypsychologist.presentation.diagnostics

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.diagnosticEntity.QuestionOfTestEntity
import com.example.mypsychologist.domain.entity.diagnosticEntity.ResultAfterSaveEntity
import com.example.mypsychologist.domain.entity.diagnosticEntity.SaveTestResultEntity
import com.example.mypsychologist.domain.entity.exerciseEntity.DailyTaskMarkIdEntity
import com.example.mypsychologist.domain.useCase.retrofitUseCase.diagnosticsUseCases.GetQuestionsOfTestByIdUseCase
import com.example.mypsychologist.domain.useCase.retrofitUseCase.diagnosticsUseCases.SaveResultTestUseCase
import com.example.mypsychologist.domain.useCase.retrofitUseCase.exerciseUseCases.MarkAsCompleteExerciseUseCase
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class PassingTestViewModel @Inject constructor(
    private val saveResultTestUseCase: SaveResultTestUseCase,
    private val getQuestionsOfTestByIdUseCase: GetQuestionsOfTestByIdUseCase,
    private val marsAsCompleteExerciseUseCase: MarkAsCompleteExerciseUseCase
) : ViewModel() {

    private val _screenState: MutableLiveData<PassingTestScreenState> = MutableLiveData()
    val screenState: LiveData<PassingTestScreenState> = _screenState

    private var _questions: List<QuestionOfTestEntity>? = null
    private val questions get() = requireNotNull(_questions)

    private var questionNumber = 0

    private val scoresForTest: IntArray by lazy {
        IntArray(questions.size)
    }

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

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveAnswerAndGoToNext(score: Int, testId: String?, taskId: String) {
        scoresForTest[questionNumber] = score
        nextQuestion(testId!!, taskId)
    }

    fun previousQuestion() {
        if (questionNumber > 0) {
            questionNumber -= 1

            viewModelScope.launch {
                _screenState.value =
                    PassingTestScreenState.Question(
                        questions[questionNumber],
                        questionNumber + 1,
                        questions.size
                    )
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun nextQuestion(testId: String, taskId: String) {
        questionNumber += 1

        viewModelScope.launch {
            if (questionNumber < questions.size) {
                _screenState.value = PassingTestScreenState.Question(
                    questions[questionNumber],
                    questionNumber + 1,
                    questions.size
                )
            }else{
                val res = saveResultTestUseCase(
                        SaveTestResultEntity(
                            testId,
                            getCurrentTimeInISO8601(),
                            scoresForTest.asList()
                        )
                )

                when (res) {
                    is Resource.Error -> PassingTestScreenState.Error(res.msg.toString())
                    Resource.Loading -> PassingTestScreenState.Loading
                    is Resource.Success -> {
                        if (taskId.isEmpty()) {
                            _screenState.value = PassingTestScreenState.Result(res.data)
                        } else {
                            markAsComplete(taskId, res.data)
                        }
                    }
                }
            }
        }
    }

    private suspend fun markAsComplete(taskId: String, data: ResultAfterSaveEntity) {
        marsAsCompleteExerciseUseCase(DailyTaskMarkIdEntity(taskId)).collect { resource ->
            when (resource) {
                is Resource.Error -> _screenState.value = PassingTestScreenState.Error(resource.msg.toString())
                Resource.Loading -> _screenState.value = PassingTestScreenState.Loading
                is Resource.Success -> _screenState.value = PassingTestScreenState.Result(data)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCurrentTimeInISO8601(): String {
        val currentTime = Instant.now()
        val formatter = DateTimeFormatter.ISO_INSTANT
        return formatter.format(currentTime)
    }
}