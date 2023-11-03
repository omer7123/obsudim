package com.example.mypsychologist.presentation.exercises

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.R
import com.example.mypsychologist.domain.entity.BeliefVerificationEntity
import com.example.mypsychologist.domain.entity.ThoughtDiaryEntity
import com.example.mypsychologist.domain.entity.ThoughtDiaryItemEntity
import com.example.mypsychologist.domain.entity.getMapOfMembers
import com.example.mypsychologist.domain.useCase.ChangeCurrentProblem
import com.example.mypsychologist.domain.useCase.GetProblemAnalysisUseCase
import com.example.mypsychologist.domain.useCase.GetProblemsUseCase
import com.example.mypsychologist.domain.useCase.SaveBeliefVerificationUseCase
import com.example.mypsychologist.ui.DelegateItem
import com.example.mypsychologist.ui.exercises.cbt.ThoughtDiaryDelegateItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class BeliefVerificationViewModel(
    private val saveBeliefVerificationUseCase: SaveBeliefVerificationUseCase
) : ViewModel() {

    private val _screenState: MutableStateFlow<NewThoughtDiaryScreenState> =
        MutableStateFlow(NewThoughtDiaryScreenState.Init)
    val screenState: StateFlow<NewThoughtDiaryScreenState>
        get() = _screenState.asStateFlow()

    private var beliefsVerification = BeliefVerificationEntity()

    fun tryToSaveBeliefVerification(type: String) {
        viewModelScope.launch {
            if (fieldsAreCorrect())
                _screenState.value =
                    NewThoughtDiaryScreenState.RequestResult(
                        saveBeliefVerificationUseCase(
                            beliefsVerification,
                            type
                        )
                    )
        }
    }

    private fun fieldsAreCorrect(): Boolean {
        var containErrors = false

        beliefsVerification.getMapOfMembers().forEach { (member, value) ->

            if (value.isEmpty()) {
                containErrors = true

                markAsNotCorrect(member)
            }

            if (containErrors)
                _screenState.value = NewThoughtDiaryScreenState.ValidationError(items)
        }

        return !containErrors
    }

    private fun markAsNotCorrect(member: String) {
        viewModelScope.launch {
            items = items.map { item ->
                if (item.content().titleId == beliefsVerification.mapOfTitles()[member])
                    ThoughtDiaryDelegateItem(item.content().copy(isNotCorrect = true))
                else
                    item
            }
        }
    }

    private fun setTruthfulnessFor(it: String) {
        beliefsVerification = beliefsVerification.copy(truthfulness = it)
    }

    private fun setConsistencyFor(it: String) {
        beliefsVerification = beliefsVerification.copy(consistency = it)
    }

    private fun setBenefitFor(it: String) {
        beliefsVerification = beliefsVerification.copy(benefit = it)
    }

    var items = listOf(
        ThoughtDiaryDelegateItem(
            ThoughtDiaryItemEntity(
                R.string.truthfulness,
                R.string.truthfulness_hint,
                0,
                ::setTruthfulnessFor
            )
        ),
        ThoughtDiaryDelegateItem(
            ThoughtDiaryItemEntity(
                R.string.consistency,
                R.string.consistency_hint,
                0,
                ::setConsistencyFor
            )
        ),
        ThoughtDiaryDelegateItem(
            ThoughtDiaryItemEntity(
                R.string.benefit,
                R.string.benefit_hint,
                0,
                ::setBenefitFor
            )
        ),
    )

    private fun BeliefVerificationEntity.mapOfTitles() =
        mapOf(
            ::truthfulness.name to R.string.situation,
            ::consistency.name to R.string.mood,
            ::benefit.name to R.string.level
        )

    class Factory @Inject constructor(
        private val saveBeliefVerificationUseCase: SaveBeliefVerificationUseCase
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return BeliefVerificationViewModel(
                saveBeliefVerificationUseCase
            ) as T
        }
    }
}