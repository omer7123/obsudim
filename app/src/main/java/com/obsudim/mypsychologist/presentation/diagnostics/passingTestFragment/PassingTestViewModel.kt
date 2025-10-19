package com.obsudim.mypsychologist.presentation.diagnostics.passingTestFragment

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.domain.entity.diagnosticEntity.QuestionOfTestEntity
import com.obsudim.mypsychologist.domain.entity.diagnosticEntity.ResultAfterSaveEntity
import com.obsudim.mypsychologist.domain.entity.diagnosticEntity.SaveTestResultEntity
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.DailyTaskMarkIdEntity
import com.obsudim.mypsychologist.domain.useCase.diagnosticsUseCases.GetQuestionsOfTestByIdUseCase
import com.obsudim.mypsychologist.domain.useCase.diagnosticsUseCases.GetTestInfoUseCase
import com.obsudim.mypsychologist.domain.useCase.diagnosticsUseCases.SaveResultTestUseCase
import com.obsudim.mypsychologist.domain.useCase.exerciseUseCases.MarkAsCompleteExerciseUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class PassingTestViewModel @Inject constructor(
    private val saveResultTestUseCase: SaveResultTestUseCase,
    private val getQuestionsOfTestByIdUseCase: GetQuestionsOfTestByIdUseCase,
    private val marsAsCompleteExerciseUseCase: MarkAsCompleteExerciseUseCase,
    private val getTestInfoUseCase: GetTestInfoUseCase,
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
            val questionsRequestDeff = async { getQuestionsOfTestByIdUseCase(testId)}
            val testInfoDeff = async { getTestInfoUseCase(testId) }

            val questionsRequest = questionsRequestDeff.await()
            val testInfo = testInfoDeff.await()

            when {
                questionsRequest is Resource.Error -> {
                    _screenState.value = PassingTestScreenState.Error("Ошибка загрузки вопросов")
                }
                testInfo is Resource.Error -> {
                    _screenState.value = PassingTestScreenState.Error("Ошибка загрузки информации о тесте")
                }
                questionsRequest is Resource.Success && testInfo is Resource.Success -> {
                    _questions = questionsRequest.data

                    _screenState.value = PassingTestScreenState.Content(
                        title = testInfo.data.title,
                        desc = testInfo.data.description,
                    )

                    _screenState.value = PassingTestScreenState.Questions(questions)
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
            _screenState.value = PassingTestScreenState.Question(
                questionNumber
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun nextQuestion(testId: String, taskId: String) {
        questionNumber += 1

        viewModelScope.launch {

            if (questionNumber < questions.size) {
                _screenState.value = PassingTestScreenState.Question(
                    questionNumber
                )
            } else {
                saveTestResult(testId, taskId)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun saveTestResult(testId: String, taskId: String) {
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
                _screenState.value = PassingTestScreenState.Result(res.data)
            }
        }
    }

    private suspend fun markAsComplete(taskId: String, data: ResultAfterSaveEntity) {
        marsAsCompleteExerciseUseCase(DailyTaskMarkIdEntity(taskId)).collect { resource ->
            when (resource) {
                is Resource.Error -> _screenState.value =
                    PassingTestScreenState.Error(resource.msg.toString())

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