package com.example.mypsychologist.presentation.diagnostics

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.domain.entity.BeckDepressionResultEntity
import com.example.mypsychologist.domain.entity.diagnosticEntity.SaveTestResultEntity
import com.example.mypsychologist.domain.entity.diagnosticEntity.ScaleResultEntity
import com.example.mypsychologist.domain.useCase.BeckDepressionTestConclusionUseCase
import com.example.mypsychologist.domain.useCase.GetDepressionBeckTestQuestionsUseCase
import com.example.mypsychologist.domain.useCase.SaveDepressionBeckResultUseCase
import com.example.mypsychologist.domain.useCase.retrofitUseCase.diagnosticsUseCases.SaveResultTestUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class BeckDepressionTestViewModel(
    getDepressionBeckTestQuestionsUseCase: GetDepressionBeckTestQuestionsUseCase,
    private val beckDepressionTestConclusionUseCase: BeckDepressionTestConclusionUseCase,
    private val saveDepressionBeckResultUseCase: SaveDepressionBeckResultUseCase,
    private val saveResultTestUseCase: SaveResultTestUseCase
) : ViewModel() {

    private val questions = getDepressionBeckTestQuestionsUseCase()

    private val answers = MutableList(questions.size) { 0 }

    private var questionNumber = 0

    private val _screenState: MutableStateFlow<BeckDepressionScreenState> =
        MutableStateFlow(
            BeckDepressionScreenState.Question(
                questions[questionNumber],
                questionNumber,
                questions.size
            )
        )
    val screenState: StateFlow<BeckDepressionScreenState>
        get() = _screenState.asStateFlow()

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
                    BeckDepressionScreenState.Question(
                        questions[questionNumber],
                        questionNumber,
                        questions.size
                    )
                } else {
                    val res = beckDepressionTestConclusionUseCase(answers)
//                    saveResultTestUseCase(
//                        SaveTestResultEntity(
//                            getCurrentTimeInISO8601(),
//                            conclusionWithScaleId(res),
//                            "d00d0df9-afc4-4c28-bc4e-963d9a643986"
//                        )
//                    )

                    BeckDepressionScreenState.Result(
                        beckDepressionTestConclusionUseCase(answers)
                    )
                }
        }
    }

//    private fun conclusionWithScaleId(res: BeckDepressionResultEntity): List<ScaleResultEntity> {
//        return listOf(
//            ScaleResultEntity("766cb35d-29d9-478f-a0a0-b92a53784ec5", res.depressionScore.first),
//            ScaleResultEntity("a0e9717f-15d4-4046-aed9-1f74e065f431", res.strategyForSeekingSocialSupport.first),
//            ScaleResultEntity("36dfe7ff-4a88-47c9-9b47-4e40d5b20242", res.avoidanceStrategy.first),
//        )
//    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCurrentTimeInISO8601(): String {
        val currentTime = Instant.now()
        val formatter = DateTimeFormatter.ISO_INSTANT.withZone(ZoneOffset.UTC)
        return formatter.format(currentTime)
    }

    fun saveResult(result: BeckDepressionResultEntity) = run {
        /*    if (!saveDepressionBeckResultUseCase(result, conclusion)) {
                _screenState.value = BeckDepressionScreenState.Error
            } */
    }

    fun previousQuestion() {
        if (questionNumber > 0) {
            questionNumber -= 1

            viewModelScope.launch {
                _screenState.value =
                    BeckDepressionScreenState.Question(
                        questions[questionNumber],
                        questionNumber,
                        questions.size
                    )
            }
        }
    }

    class Factory @Inject constructor(
        private val getDepressionBeckTestQuestionsUseCase: GetDepressionBeckTestQuestionsUseCase,
        private val beckDepressionTestResultUseCase: BeckDepressionTestConclusionUseCase,
        private val saveDepressionBeckResultUseCase: SaveDepressionBeckResultUseCase,
        private val saveResultTestUseCase: SaveResultTestUseCase

    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return BeckDepressionTestViewModel(
                getDepressionBeckTestQuestionsUseCase,
                beckDepressionTestResultUseCase,
                saveDepressionBeckResultUseCase,
                saveResultTestUseCase
            ) as T
        }
    }
}