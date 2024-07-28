package com.example.mypsychologist.presentation.diagnostics

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.domain.entity.CSIResultEntity
import com.example.mypsychologist.domain.entity.diagnosticEntity.SaveTestResultEntity
import com.example.mypsychologist.domain.entity.diagnosticEntity.ScaleResultEntity
import com.example.mypsychologist.domain.useCase.CSITestConclusionUseCase
import com.example.mypsychologist.domain.useCase.GetCSITestUseCase
import com.example.mypsychologist.domain.useCase.SaveCSIResultUseCase
import com.example.mypsychologist.domain.useCase.retrofitUseCase.diagnosticsUseCases.SaveResultTestUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class CSITestViewModel(
    getCSITestUseCase: GetCSITestUseCase,
    private val csiConclusionUseCase: CSITestConclusionUseCase,
    private val saveCSIResultUseCase: SaveCSIResultUseCase,
    private val saveResultTestUseCase: SaveResultTestUseCase
) : ViewModel() {
    private val questions = getCSITestUseCase()
    private val answers = MutableList(questions.size) { 0 }
    private var questionNumber = 0

    private val _screenState: MutableStateFlow<CSIScreenState> =
        MutableStateFlow(
            CSIScreenState.Question(
                questions[questionNumber],
                questionNumber,
                questions.size
            )
        )

    val screenState: StateFlow<CSIScreenState>
        get() = _screenState

    fun saveAnswerAndGoToNext(score: Int) {
        if (questionNumber == 5)
            answers[questionNumber] = 6 - score
        else
            answers[questionNumber] = score
        nextQuestion()
    }

    fun save(result: CSIResultEntity) {
        if (!saveCSIResultUseCase(result))
            _screenState.value = CSIScreenState.Error
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun nextQuestion() {
        questionNumber += 1

        viewModelScope.launch {

            _screenState.value =
                if (questionNumber < questions.size) {
                    CSIScreenState.Question(
                        questions[questionNumber],
                        questionNumber,
                        questions.size
                    )
                } else {
                    val res = csiConclusionUseCase(answers)
                    saveResultTestUseCase(
                        SaveTestResultEntity(
                            getCurrentTimeInISO8601(),
                            conclusionWithScaleId(res),
                            "9a1a674f-246f-4e80-96c6-e0cc5f5d148a"
                        )
                    )
                    CSIScreenState.Result(csiConclusionUseCase(answers))
                }
        }
    }

    private fun conclusionWithScaleId(res: CSIResultEntity): List<ScaleResultEntity> {
        return listOf(
            ScaleResultEntity("d539e01c-2e86-40a5-9928-678cbf18e5fc", res.problemResolutionStrategy.first),
            ScaleResultEntity("a0e9717f-15d4-4046-aed9-1f74e065f431", res.strategyForSeekingSocialSupport.first),
            ScaleResultEntity("36dfe7ff-4a88-47c9-9b47-4e40d5b20242", res.avoidanceStrategy.first),
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
                    CSIScreenState.Question(
                        questions[questionNumber],
                        questionNumber,
                        questions.size
                    )
            }
        }
    }

    class Factory @Inject constructor(
        private val getCSITestUseCase: GetCSITestUseCase,
        private val csiConclusionUseCase: CSITestConclusionUseCase,
        private val saveCSIResultUseCase: SaveCSIResultUseCase,
        private val saveResultTestUseCase: SaveResultTestUseCase

    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return CSITestViewModel(
                getCSITestUseCase, csiConclusionUseCase, saveCSIResultUseCase, saveResultTestUseCase
            ) as T
        }
    }
}