package com.example.mypsychologist.presentation.exercises

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.R
import com.example.mypsychologist.domain.entity.ProblemAnalysisEntity
import com.example.mypsychologist.domain.entity.ThoughtDiaryItemEntity
import com.example.mypsychologist.domain.useCase.SaveProblemAnalysisUseCase
import com.example.mypsychologist.domain.useCase.SaveThoughtDiaryUseCase
import com.example.mypsychologist.ui.exercises.cbt.ThoughtDiaryDelegateItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProblemAnalysisViewModel(private val saveProblemAnalysisUseCase: SaveProblemAnalysisUseCase) :
    ViewModel() {

    private val _screenState: MutableStateFlow<NewThoughtDiaryScreenState> =
        MutableStateFlow(NewThoughtDiaryScreenState.Init)
    val screenState: StateFlow<NewThoughtDiaryScreenState>
        get() = _screenState.asStateFlow()

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
        viewModelScope.launch {
            if (preferenceIsCorrect()) {
                _screenState.value =
                    NewThoughtDiaryScreenState.RequestResult(
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
            _screenState.value = NewThoughtDiaryScreenState.ValidationError(
                _harmfulThought.map {
                    if (it.content().titleId == R.string.flexible_preference)
                        ThoughtDiaryDelegateItem(it.content().copy(isNotCorrect = true))
                    else
                        it
                })
            false
        }

    fun requirementIsCorrect(): Boolean =
        if (analysis.dogmaticRequirement.isNotEmpty()) {
            _screenState.value = NewThoughtDiaryScreenState.Init
            true
        }
        else {
            _screenState.value = NewThoughtDiaryScreenState.ValidationError(
                _harmfulThought.map {
                    if (it.content().titleId == R.string.dogmatic_requirement)
                        ThoughtDiaryDelegateItem(it.content().copy(isNotCorrect = true))
                    else
                        it
                })
            false
        }

    private val _harmfulThought = mutableListOf(
        ThoughtDiaryDelegateItem(
            ThoughtDiaryItemEntity(
                R.string.dogmatic_requirement,
                R.string.dogmatic_requirement_hint,
                0,
                ::setRequirement
            )
        ),
        ThoughtDiaryDelegateItem(
            ThoughtDiaryItemEntity(
                R.string.dramatization,
                R.string.dramatization_hint,
                0,
                ::setDramatization
            )
        ),
        ThoughtDiaryDelegateItem(
            ThoughtDiaryItemEntity(
                R.string.LFT,
                R.string.LFT_hint,
                0,
                ::setLFT
            )
        ),
        ThoughtDiaryDelegateItem(
            ThoughtDiaryItemEntity(
                R.string.humiliating_remarks,
                R.string.humiliating_remarks_hint,
                0,
                ::setRemarks
            )
        )
    )

    val harmfulThought: List<ThoughtDiaryDelegateItem>
        get() = _harmfulThought

    private val _alternativeThought = listOf(
        ThoughtDiaryDelegateItem(
            ThoughtDiaryItemEntity(
                R.string.flexible_preference,
                R.string.flexible_preference_hint,
                0,
                ::setPreference
            )
        ),
        ThoughtDiaryDelegateItem(
            ThoughtDiaryItemEntity(
                R.string.perspective,
                R.string.perspective_hint,
                0,
                ::setPerspective
            )
        ),
        ThoughtDiaryDelegateItem(
            ThoughtDiaryItemEntity(
                R.string.HFT,
                R.string.HFT_hint,
                0,
                ::setHFT
            )
        ),
        ThoughtDiaryDelegateItem(
            ThoughtDiaryItemEntity(
                R.string.unconditional_acceptance,
                R.string.unconditional_acceptance_hint,
                0,
                ::setAcceptance
            )
        )
    )

    val alternativeThought: List<ThoughtDiaryDelegateItem>
        get() = _alternativeThought

    class Factory @Inject constructor(private val saveProblemAnalysisUseCase: SaveProblemAnalysisUseCase) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return ProblemAnalysisViewModel(saveProblemAnalysisUseCase) as T
        }
    }
}