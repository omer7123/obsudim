package com.example.mypsychologist.presentation.diagnostics

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.domain.entity.JASResultEntity
import com.example.mypsychologist.domain.entity.diagnosticEntity.SaveTestResultEntity
import com.example.mypsychologist.domain.entity.diagnosticEntity.ScaleResultEntity
import com.example.mypsychologist.domain.useCase.GetJASTestUseCase
import com.example.mypsychologist.domain.useCase.JASConclusionUseCase
import com.example.mypsychologist.domain.useCase.SaveJASResultUseCase
import com.example.mypsychologist.domain.useCase.retrofitUseCase.diagnosticsUseCases.SaveResultTestUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class JASTestViewModel(
    getJASTestUseCase: GetJASTestUseCase,
    private val jasConclusionUseCase: JASConclusionUseCase,
    private val saveJASResultUseCase: SaveJASResultUseCase,
    private val saveResultTestUseCase: SaveResultTestUseCase
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

    @RequiresApi(Build.VERSION_CODES.O)
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
                    val res = jasConclusionUseCase(answers)

//                    saveResultTestUseCase(
//                        SaveTestResultEntity(
//                            getCurrentTimeInISO8601(),
//                            conclusionWithScaleId(res),
//                            "0923a7a8-510c-41d7-a273-3c7bd5481ba3"
//                        )
//                    )
                    JASScreenState.Result(jasConclusionUseCase(answers))
                }
        }
    }

    private fun conclusionWithScaleId(res: JASResultEntity): List<ScaleResultEntity> {
        return listOf(
            ScaleResultEntity("5b91710f-5eb1-4ccc-bb38-5c639fa0d4ad", res.apatheticActions.first),
            ScaleResultEntity("85799862-db67-4894-8e78-00d13180c3df", res.apatheticThoughts.first),
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCurrentTimeInISO8601(): String {
        val currentTime = Instant.now()
        val formatter = DateTimeFormatter.ISO_INSTANT.withZone(ZoneOffset.UTC)
        return formatter.format(currentTime)
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
        private val saveJASResultUseCase: SaveJASResultUseCase,
        private val saveResultTestUseCase: SaveResultTestUseCase

    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return JASTestViewModel(
                getJASTestUseCase, jasConclusionUseCase, saveJASResultUseCase, saveResultTestUseCase
            ) as T
        }
    }
}