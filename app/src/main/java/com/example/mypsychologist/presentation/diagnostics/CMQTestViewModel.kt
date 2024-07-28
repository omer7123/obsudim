package com.example.mypsychologist.presentation.diagnostics

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.domain.entity.ResultWithScalesEntity
import com.example.mypsychologist.domain.entity.diagnosticEntity.SaveTestResultEntity
import com.example.mypsychologist.domain.entity.diagnosticEntity.ScaleResultEntity
import com.example.mypsychologist.domain.useCase.CMQConclusionUseCase
import com.example.mypsychologist.domain.useCase.GetCMQTestUseCase
import com.example.mypsychologist.domain.useCase.SaveCMQResultUseCase
import com.example.mypsychologist.domain.useCase.retrofitUseCase.diagnosticsUseCases.SaveResultTestUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class CMQTestViewModel(
    getSMQTestUseCase: GetCMQTestUseCase,
    private val CMQConclusionUseCase: CMQConclusionUseCase,
    private val saveCMQResultUseCase: SaveCMQResultUseCase,
    private val saveResultTestUseCase: SaveResultTestUseCase

) : ViewModel() {

    private val questions = getSMQTestUseCase()

    private val answers = MutableList(questions.size) { 0 }

    private var questionNumber = 0

    private val _screenState: MutableStateFlow<CMQScreenState> =
        MutableStateFlow(
            CMQScreenState.Question(
                questions[questionNumber],
                questionNumber,
                questions.size
            )
        )
    val screenState: StateFlow<CMQScreenState>
        get() = _screenState.asStateFlow()

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveAnswerAndGoToNext(score: Int) {
        answers[questionNumber] = score

        nextQuestion()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun nextQuestion() {
        questionNumber += 1

        viewModelScope.launch {

            _screenState.value =
                if (questionNumber < questions.size) {
                    CMQScreenState.Question(
                        questions[questionNumber],
                        questionNumber,
                        questions.size
                    )
                } else {
                    val res = CMQConclusionUseCase(answers)
//                    saveResultTestUseCase(
//                        SaveTestResultEntity(
//                            getCurrentTimeInISO8601(),
//                            conclusionWithScaleId(res),
//                            "be28c8c4-18e9-4c2b-a3de-3b73dc50d929"
//                        )
//                    )
                    CMQScreenState.Result(
                        CMQConclusionUseCase(answers)
                    )
                }
        }
    }

    private fun conclusionWithScaleId(res: ResultWithScalesEntity): List<ScaleResultEntity> {
        return listOf(
//            ScaleResultEntity("5b91710f-5eb1-4ccc-bb38-5c639fa0d4ad", res.conclusion..first),
//            ScaleResultEntity("85799862-db67-4894-8e78-00d13180c3df", res.depersonalization.first),
//            ScaleResultEntity("213333b7-3408-42e5-bd83-5d41e1444738", res.reductionOfPersonalAchievements.first),
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
                    CMQScreenState.Question(
                        questions[questionNumber],
                        questionNumber,
                        questions.size
                    )
            }
        }
    }

    fun saveResult(result: Int, conclusion: String) = run {
        if (!saveCMQResultUseCase(result, conclusion)) {
            _screenState.value = CMQScreenState.Error
        }
    }

    class Factory @Inject constructor(
        private val getSMQTestUseCase: GetCMQTestUseCase,
        private val CMQConclusionUseCase: CMQConclusionUseCase,
        private val saveCMQResultUseCase: SaveCMQResultUseCase,
        private val saveResultTestUseCase: SaveResultTestUseCase

    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return CMQTestViewModel(
                getSMQTestUseCase, CMQConclusionUseCase, saveCMQResultUseCase, saveResultTestUseCase
            ) as T
        }
    }
}