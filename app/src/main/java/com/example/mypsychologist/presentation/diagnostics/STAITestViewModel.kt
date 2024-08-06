package com.example.mypsychologist.presentation.diagnostics

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.domain.entity.STAIResultEntity
import com.example.mypsychologist.domain.entity.diagnosticEntity.SaveTestResultEntity
import com.example.mypsychologist.domain.entity.diagnosticEntity.ScaleResultEntity
import com.example.mypsychologist.domain.useCase.GetSTAITestUseCase
import com.example.mypsychologist.domain.useCase.STAIConclusionUseCase
import com.example.mypsychologist.domain.useCase.SaveSTAIResultUseCase
import com.example.mypsychologist.domain.useCase.retrofitUseCase.diagnosticsUseCases.SaveResultTestUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class STAITestViewModel(
    getSTAITestUseCase: GetSTAITestUseCase,
    private val staiConclusionUseCase: STAIConclusionUseCase,
    private val saveSTAIResultUseCase: SaveSTAIResultUseCase,
    private val saveResultTestUseCase: SaveResultTestUseCase
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

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveAnswerAndGoToNext(score: Int) {
        answers[questionNumber] = score

        nextQuestion()
    }

    fun save(result: STAIResultEntity) {
        if (!saveSTAIResultUseCase(result))
            _screenState.value = STAIScreenState.Error
    }

    @RequiresApi(Build.VERSION_CODES.O)
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
                    val res = staiConclusionUseCase(answers)
//                    saveResultTestUseCase(SaveTestResultEntity(
//                        getCurrentTimeInISO8601(),
//                        conclusionWithScaleId(res),
//                        "7bdebf8d-dfc5-417a-b583-d8364baed8d5"
//                    ))
                    STAIScreenState.Result(staiConclusionUseCase(answers))
                }
        }
    }

    private fun conclusionWithScaleId(res: STAIResultEntity): List<ScaleResultEntity> {
        return listOf(
            ScaleResultEntity("92c05518-d5b1-4a73-9bbf-e9bb2996a116", res.situationalAnxiety.first),
            ScaleResultEntity("0f9f30f5-14ee-43a9-8d9d-cf9b2390c8ff", res.personalAnxiety.first),
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
        private val saveSTAIResultUseCase: SaveSTAIResultUseCase,
        private val saveResultTestUseCase: SaveResultTestUseCase

    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return STAITestViewModel(
                getSTAITestUseCase, staiConclusionUseCase, saveSTAIResultUseCase, saveResultTestUseCase
            ) as T
        }
    }
}