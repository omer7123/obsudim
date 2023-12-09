package com.example.mypsychologist.presentation.exercises

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.R
import com.example.mypsychologist.domain.entity.BeliefAnalysisEntity
import com.example.mypsychologist.domain.entity.BeliefVerificationEntity
import com.example.mypsychologist.domain.entity.ThoughtDiaryItemEntity
import com.example.mypsychologist.domain.entity.getMapOfMembers
import com.example.mypsychologist.domain.useCase.GetBeliefAnalysisUseCase
import com.example.mypsychologist.domain.useCase.SaveBeliefAnalysisUseCase
import com.example.mypsychologist.domain.useCase.SaveBeliefVerificationUseCase
import com.example.mypsychologist.ui.exercises.cbt.ThoughtDiaryDelegateItem
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class BeliefAnalysisViewModel @AssistedInject constructor(
    private val saveBeliefAnalysisUseCase: SaveBeliefAnalysisUseCase,
    private val getBeliefAnalysisUseCase: GetBeliefAnalysisUseCase,
    @Assisted("type") private val type: String,
) : ViewModel() {

    private val _screenState: MutableStateFlow<BeliefVerificationScreenState> =
        MutableStateFlow(BeliefVerificationScreenState.Init)
    val screenState: StateFlow<BeliefVerificationScreenState>
        get() = _screenState.asStateFlow()

    private var beliefAnalysisEntity = BeliefAnalysisEntity()

    init {
        _screenState.value = BeliefVerificationScreenState.Loading
        viewModelScope.launch {
            getSavedAnalysis()
        }
    }

    private suspend fun getSavedAnalysis() {
        beliefAnalysisEntity = getBeliefAnalysisUseCase(type)

        beliefAnalysisEntity.getMapOfMembers().forEach { (key, value) ->

            _items = items.map {
                if (it.content().fieldName == key)
                    ThoughtDiaryDelegateItem(
                        it.content().copy(text = value)
                    )
                else it
            }.toMutableList()
        }

        _screenState.value = BeliefVerificationScreenState.Data(items)
    }

    fun tryToSaveBeliefAnalysis(type: String) {
        viewModelScope.launch {
            if (fieldsAreCorrect())
                _screenState.value =
                    BeliefVerificationScreenState.RequestResult(
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
                _screenState.value = BeliefVerificationScreenState.ValidationError(items)
        }

        return !containErrors
    }

    private fun markAsNotCorrect(member: String) {
        viewModelScope.launch {
            _items = items.map { item ->
                if (item.content().titleId == beliefAnalysisEntity.mapOfTitles()[member])
                    ThoughtDiaryDelegateItem(item.content().copy(isNotCorrect = true))
                else
                    item
            }
        }
    }

    private var _items = listOf(
        ThoughtDiaryDelegateItem(
            ThoughtDiaryItemEntity(
                R.string.fillings_and_actions,
                R.string.fillings_and_actions_hint,
                0,
                ::setFillingsAndActions,
                BeliefAnalysisEntity::feelingsAndActions.name
            )
        ),
        ThoughtDiaryDelegateItem(
            ThoughtDiaryItemEntity(
                R.string.motivation,
                R.string.motivation_hint,
                0,
                ::setMotivations,
                BeliefAnalysisEntity::motivation.name
            )
        ),
        ThoughtDiaryDelegateItem(
            ThoughtDiaryItemEntity(
                R.string.interferences,
                R.string.interferences_hint,
                0,
                ::setInterferences,
                BeliefAnalysisEntity::interferences.name
            )
        ),
        ThoughtDiaryDelegateItem(
            ThoughtDiaryItemEntity(
                R.string.dependent,
                R.string.dependent_hint,
                0,
                ::setDependent,
                BeliefAnalysisEntity::dependent.name
            )
        ),
        ThoughtDiaryDelegateItem(
            ThoughtDiaryItemEntity(
                R.string.results,
                R.string.results_hint,
                0,
                ::setResults,
                BeliefAnalysisEntity::results.name
            )
        )
    )

    val items
        get() = _items

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

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("type") type: String
        ): BeliefAnalysisViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: Factory,
            type: String
        ) = object : ViewModelProvider.Factory {

            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                assistedFactory.create(type) as T
        }
    }
}