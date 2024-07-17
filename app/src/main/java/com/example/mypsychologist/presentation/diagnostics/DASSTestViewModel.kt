package com.example.mypsychologist.presentation.diagnostics


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.domain.entity.DASSResultEntity
import com.example.mypsychologist.domain.entity.diagnosticEntity.SaveTestResultEntity
import com.example.mypsychologist.domain.entity.diagnosticEntity.ScaleResultEntity
import com.example.mypsychologist.domain.useCase.DASSConclusionUseCase
import com.example.mypsychologist.domain.useCase.GetDASSTestUseCase
import com.example.mypsychologist.domain.useCase.SaveDASSResultUseCase
import com.example.mypsychologist.domain.useCase.retrofitUseCase.diagnosticsUseCases.SaveResultTestUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class DASSTestViewModel(
    getDASSTestUseCase: GetDASSTestUseCase,
    private val saveResultTestUseCase: SaveResultTestUseCase,
    private val DASSConclusionUseCase: DASSConclusionUseCase,
    private val saveDASSResultUseCase: SaveDASSResultUseCase
) : ViewModel() {

    private val questions = getDASSTestUseCase()

    private val answers = MutableList(questions.size) { 0 }

    private var questionNumber = 0

    private val _screenState: MutableStateFlow<DASSScreenState> =
        MutableStateFlow(
            DASSScreenState.Question(
                questions[questionNumber],
                questionNumber,
                questions.size
            )
        )
    val screenState: StateFlow<DASSScreenState>
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
                    DASSScreenState.Question(
                        questions[questionNumber],
                        questionNumber,
                        questions.size
                    )
                } else {
                    val res = DASSConclusionUseCase(answers)
                    saveResultTestUseCase(
                        SaveTestResultEntity(
                            getCurrentTimeInISO8601(),
                            conclusionWithScaleId(res),
                            "7921dc6c-0933-45b3-a97c-aeb5ef76c865"
                        )
                    )
                    DASSScreenState.Result(DASSConclusionUseCase(answers))
                }
        }
    }

    private fun conclusionWithScaleId(res: DASSResultEntity): List<ScaleResultEntity> {
        return listOf(
            ScaleResultEntity("0dcfd5da-9065-4079-bced-202039b5973f", res.stressScoreAndConclusion.first),
            ScaleResultEntity("e5d218da-d944-4239-9126-0cd5c7bedc3c", res.anxietyScoreAndConclusion.first),
            ScaleResultEntity("1ec3cf7b-3e33-48bc-8c7b-8525064003a0", res.depressionScoreAndConclusion.first),
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
                    DASSScreenState.Question(
                        questions[questionNumber],
                        questionNumber,
                        questions.size
                    )
            }
        }
    }

    fun save(result: DASSResultEntity) {
        if (!saveDASSResultUseCase(result))
            _screenState.value = DASSScreenState.Error
    }

    class Factory @Inject constructor(
        private val getDASSTestUseCase: GetDASSTestUseCase,
        private val DASSConclusionUseCase: DASSConclusionUseCase,
        private val saveDASSResultUseCase: SaveDASSResultUseCase,
        private val saveResultTestUseCase: SaveResultTestUseCase
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return DASSTestViewModel(
                getDASSTestUseCase,
                saveResultTestUseCase,
                DASSConclusionUseCase,
                saveDASSResultUseCase
            ) as T
        }
    }
}