package com.example.mypsychologist.presentation.exercises

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.R
import com.example.mypsychologist.domain.entity.InputItemEntity
import com.example.mypsychologist.domain.entity.ProblemAnalysisEntity
import com.example.mypsychologist.domain.entity.ThoughtDiaryItemEntity
import com.example.mypsychologist.domain.entity.getMapOfFilledMembers
import com.example.mypsychologist.domain.useCase.GetProblemAnalysisEntityUseCase
import com.example.mypsychologist.domain.useCase.SaveProblemAnalysisUseCase
import com.example.mypsychologist.ui.exercises.cbt.InputDelegateItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProblemAnalysisViewModel(
    private val saveProblemAnalysisUseCase: SaveProblemAnalysisUseCase,
    private val getProblemAnalysisUseCase: GetProblemAnalysisEntityUseCase
) :
    ViewModel() {

    private val _screenState: MutableStateFlow<ThoughtAnalysisScreenState> =
        MutableStateFlow(ThoughtAnalysisScreenState.Init)
    val screenState: StateFlow<ThoughtAnalysisScreenState>
        get() = _screenState.asStateFlow()

    init {
        _screenState.value = ThoughtAnalysisScreenState.Loading
        viewModelScope.launch {
            getSavedAnalysis()
        }
    }

    private suspend fun getSavedAnalysis() {
        analysis = getProblemAnalysisUseCase()

        analysis.getMapOfFilledMembers().forEach { (key, value) ->

            _harmfulThought = harmfulThought.map {
                if (it.content().fieldName == key) InputDelegateItem(
                    it.content().copy(text = value)
                ) else it
            }.toMutableList()

            _alternativeThought = alternativeThought.map {
                if (it.content().fieldName == key) InputDelegateItem(
                    it.content().copy(text = value)
                ) else it
            }.toMutableList()
        }

        _screenState.value = ThoughtAnalysisScreenState.Data(
            Pair(harmfulThought, alternativeThought)
        )
    }

    private var analysis = ProblemAnalysisEntity()

    private fun setRequirement(it: String) {
        analysis = analysis.copy(dogmaticRequirement = it)
    }

    private fun setDramatization(it: String) {
        analysis = analysis.copy(dramatization = it)
    }

    private fun setLFT(it: String) {
        analysis = analysis.copy(lft = it)
    }

    private fun setRemarks(it: String) {
        analysis = analysis.copy(humiliatingRemarks = it)
    }

    private fun setPreference(it: String) {
        analysis = analysis.copy(flexiblePreference = it)
    }

    private fun setPerspective(it: String) {
        analysis = analysis.copy(perspective = it)
    }

    private fun setHFT(it: String) {
        analysis = analysis.copy(hft = it)
    }

    private fun setAcceptance(it: String) {
        analysis = analysis.copy(unconditionalAcceptance = it)
    }

    fun tryToSaveDiary() {
        _screenState.value = ThoughtAnalysisScreenState.Loading
        
        viewModelScope.launch {
            if (preferenceIsCorrect()) {
                _screenState.value =
                    ThoughtAnalysisScreenState.RequestResult(
                        saveProblemAnalysisUseCase(
                            analysis
                        )
                    )
            }
        }
    }

    private fun preferenceIsCorrect(): Boolean =
        if (analysis.flexiblePreference.isNotEmpty())
            true
        else {
            _screenState.value = ThoughtAnalysisScreenState.ValidationError(
                _harmfulThought.map {
                    if (it.content().titleId == R.string.flexible_preference)
                        InputDelegateItem(it.content().copy(isNotCorrect = true))
                    else
                        it
                })
            false
        }

    fun requirementIsCorrect(): Boolean =
        if (analysis.dogmaticRequirement.isNotEmpty()) {
            _screenState.value = ThoughtAnalysisScreenState.Init
            true
        } else {
            _screenState.value = ThoughtAnalysisScreenState.ValidationError(
                _harmfulThought.map {
                    if (it.content().titleId == R.string.dogmatic_requirement)
                        InputDelegateItem(it.content().copy(isNotCorrect = true))
                    else
                        it
                })
            false
        }

    private var _harmfulThought = mutableListOf(
        InputDelegateItem(
            InputItemEntity(
                R.string.dogmatic_requirement,
                R.string.dogmatic_requirement_hint,
                R.string.dogmatic_requirement_help,
                ::setRequirement,
                ProblemAnalysisEntity::dogmaticRequirement.name
            )
        ),
        InputDelegateItem(
            InputItemEntity(
                R.string.dramatization,
                R.string.dramatization_hint,
                R.string.dramatization_help,
                ::setDramatization,
                ProblemAnalysisEntity::dramatization.name
            )
        ),
        InputDelegateItem(
            InputItemEntity(
                R.string.LFT,
                R.string.LFT_hint,
                R.string.LFT_help,
                ::setLFT,
                ProblemAnalysisEntity::lft.name
            )
        ),
        InputDelegateItem(
            InputItemEntity(
                R.string.humiliating_remarks,
                R.string.humiliating_remarks_hint,
                R.string.humiliating_remarks_help,
                ::setRemarks,
                ProblemAnalysisEntity::humiliatingRemarks.name
            )
        )
    )

    val harmfulThought: List<InputDelegateItem>
        get() = _harmfulThought

    private var _alternativeThought = listOf(
        InputDelegateItem(
            InputItemEntity(
                R.string.flexible_preference,
                R.string.flexible_preference_hint,
                R.string.flexible_preference_help,
                ::setPreference,
                ProblemAnalysisEntity::flexiblePreference.name
            )
        ),
        InputDelegateItem(
            InputItemEntity(
                R.string.perspective,
                R.string.perspective_hint,
                R.string.perspective_help,
                ::setPerspective,
                ProblemAnalysisEntity::perspective.name
            )
        ),
        InputDelegateItem(
            InputItemEntity(
                R.string.HFT,
                R.string.HFT_hint,
                R.string.HFT_help,
                ::setHFT,
                ProblemAnalysisEntity::hft.name
            )
        ),
        InputDelegateItem(
            InputItemEntity(
                R.string.unconditional_acceptance,
                R.string.unconditional_acceptance_hint,
                R.string.unconditional_acceptance_help,
                ::setAcceptance,
                ProblemAnalysisEntity::unconditionalAcceptance.name
            )
        )
    )

    val alternativeThought: List<InputDelegateItem>
        get() = _alternativeThought

    class Factory @Inject constructor(
        private val saveProblemAnalysisUseCase: SaveProblemAnalysisUseCase,
        private val getProblemAnalysisUseCase: GetProblemAnalysisEntityUseCase
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return ProblemAnalysisViewModel(
                saveProblemAnalysisUseCase,
                getProblemAnalysisUseCase
            ) as T
        }
    }
}