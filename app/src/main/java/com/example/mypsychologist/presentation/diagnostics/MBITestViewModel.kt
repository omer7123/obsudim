package com.example.mypsychologist.presentation.diagnostics

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.domain.entity.MBIResultEntity
import com.example.mypsychologist.domain.entity.diagnosticEntity.SaveTestResultEntity
import com.example.mypsychologist.domain.entity.diagnosticEntity.ScaleResultEntity
import com.example.mypsychologist.domain.useCase.GetMBITestUseCase
import com.example.mypsychologist.domain.useCase.MBIConclusionUseCase
import com.example.mypsychologist.domain.useCase.SaveMBIResultUseCase
import com.example.mypsychologist.domain.useCase.retrofitUseCase.diagnosticsUseCases.SaveResultTestUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class MBITestViewModel(
    getMBITestUseCase: GetMBITestUseCase,
    private val mbiConclusionUseCase: MBIConclusionUseCase,
    private val saveMBIResultUseCase: SaveMBIResultUseCase,
    private val saveResultTestUseCase: SaveResultTestUseCase

) : ViewModel() {
    private val questions = getMBITestUseCase()
    private val answers = MutableList(questions.size) { 0 }
    private var questionNumber = 0

    private val _screenState: MutableStateFlow<MBIScreenState> =
        MutableStateFlow(
            MBIScreenState.Question(
                questions[questionNumber],
                questionNumber,
                questions.size
            )
        )

    val screenState: StateFlow<MBIScreenState>
        get() = _screenState

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveAnswerAndGoToNext(score: Int) {
        if (questionNumber == 5)
            answers[questionNumber] = 6 - score
        else
            answers[questionNumber] = score
        nextQuestion()
    }

    fun save(result: MBIResultEntity) {
        if (!saveMBIResultUseCase(result))
            _screenState.value = MBIScreenState.Error
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun nextQuestion() {
        questionNumber += 1

        viewModelScope.launch {

            _screenState.value =
                if (questionNumber < questions.size) {
                    MBIScreenState.Question(
                        questions[questionNumber],
                        questionNumber,
                        questions.size
                    )
                } else {
                    val res = mbiConclusionUseCase(answers)
                    saveResultTestUseCase(
                        SaveTestResultEntity(
                            getCurrentTimeInISO8601(),
                            conclusionWithScaleId(res),
                            "be28c8c4-18e9-4c2b-a3de-3b73dc50d929"
                        )
                    )
                    MBIScreenState.Result(mbiConclusionUseCase(answers))
                }
        }
    }

    private fun conclusionWithScaleId(res: MBIResultEntity): List<ScaleResultEntity> {
        return listOf(
            ScaleResultEntity("212f8d30-b7d5-485f-ad35-c75d33db719c", res.emotionalExhaustion.first),
            ScaleResultEntity("d92268c7-ee10-4fbb-a32b-b6d02518f37e", res.depersonalization.first),
            ScaleResultEntity("fc09cb2f-b981-409b-a256-f205fbabcd73", res.reductionOfPersonalAchievements.first),
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
                    MBIScreenState.Question(
                        questions[questionNumber],
                        questionNumber,
                        questions.size
                    )
            }
        }
    }

    class Factory @Inject constructor(
        private val getMBITestUseCase: GetMBITestUseCase,
        private val mbiConclusionUseCase: MBIConclusionUseCase,
        private val saveMBIResultUseCase: SaveMBIResultUseCase,
        private val saveResultTestUseCase: SaveResultTestUseCase
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return MBITestViewModel(
                getMBITestUseCase, mbiConclusionUseCase, saveMBIResultUseCase, saveResultTestUseCase
            ) as T
        }
    }
}