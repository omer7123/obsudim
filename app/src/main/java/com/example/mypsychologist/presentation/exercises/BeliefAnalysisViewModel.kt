package com.example.mypsychologist.presentation.exercises

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.R
import com.example.mypsychologist.domain.entity.BeliefAnalysisEntity
import com.example.mypsychologist.domain.entity.BeliefVerificationEntity
import com.example.mypsychologist.domain.entity.ThoughtDiaryItemEntity
import com.example.mypsychologist.domain.entity.getMapOfMembers
import com.example.mypsychologist.domain.useCase.SaveBeliefAnalysisUseCase
import com.example.mypsychologist.domain.useCase.SaveBeliefVerificationUseCase
import com.example.mypsychologist.ui.exercises.cbt.ThoughtDiaryDelegateItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class BeliefAnalysisViewModel(private val saveBeliefAnalysisUseCase: SaveBeliefAnalysisUseCase) : ViewModel() {

    private val _screenState: MutableStateFlow<NewThoughtDiaryScreenState> =
        MutableStateFlow(NewThoughtDiaryScreenState.Init)
    val screenState: StateFlow<NewThoughtDiaryScreenState>
        get() = _screenState.asStateFlow()

    private var beliefAnalysisEntity = BeliefAnalysisEntity()

    fun tryToSaveBeliefAnalysis(type: String) {
        viewModelScope.launch {
            if (fieldsAreCorrect())
                _screenState.value =
                    NewThoughtDiaryScreenState.RequestResult(
                        saveBeliefAnalysisUseCase(
                            beliefAnalysisEntity,
                            type
                        )
                    )
        }
    }

    private fun fieldsAreCorrect(): Boolean {
        var containErrors = false

        beliefAnalysisEntity.getMapOfMembers().forEach { (member, value) ->

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
                if (item.content().titleId == beliefAnalysisEntity.mapOfTitles()[member])
                    ThoughtDiaryDelegateItem(item.content().copy(isNotCorrect = true))
                else
                    item
            }
        }
    }

    var items = listOf(
        ThoughtDiaryDelegateItem(
            ThoughtDiaryItemEntity(
                R.string.fillings_and_actions,
                R.string.fillings_and_actions_hint,
                0,
                ::setFillingsAndActions
            )
        ),
        ThoughtDiaryDelegateItem(
            ThoughtDiaryItemEntity(
                R.string.motivation,
                R.string.motivation_hint,
                0,
                ::setMotivations
            )
        ),
        ThoughtDiaryDelegateItem(
            ThoughtDiaryItemEntity(
                R.string.interferences,
                R.string.interferences_hint,
                0,
                ::setInterferences
            )
        ),
        ThoughtDiaryDelegateItem(
            ThoughtDiaryItemEntity(
                R.string.dependent,
                R.string.dependent_hint,
                0,
                ::setDependent
            )
        ),
        ThoughtDiaryDelegateItem(
            ThoughtDiaryItemEntity(
                R.string.results,
                R.string.results_hint,
                0,
                ::setResults
            )
        )
    )

    private fun setFillingsAndActions(it: String) {
        beliefAnalysisEntity = beliefAnalysisEntity.copy(feelingsAndActions = it)
    }

    private fun setMotivations(it: String) {
        beliefAnalysisEntity = beliefAnalysisEntity.copy(motivation = it)
    }

    private fun setInterferences(it: String) {
        beliefAnalysisEntity = beliefAnalysisEntity.copy(interferences = it)
    }

    private fun setDependent(it: String) {
        beliefAnalysisEntity = beliefAnalysisEntity.copy(dependent = it)
    }

    private fun setResults(it: String) {
        beliefAnalysisEntity = beliefAnalysisEntity.copy(results = it)
    }

    private fun BeliefAnalysisEntity.mapOfTitles() =
        mapOf(
            ::feelingsAndActions.name to R.string.fillings_and_actions,
            ::motivation.name to R.string.motivation,
            ::interferences.name to R.string.interferences,
            ::dependent.name to R.string.dependent,
            ::results.name to R.string.results
        )

    class Factory @Inject constructor(
        private val saveBeliefAnalysisUseCase: SaveBeliefAnalysisUseCase
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return BeliefAnalysisViewModel(saveBeliefAnalysisUseCase) as T
        }
    }
}