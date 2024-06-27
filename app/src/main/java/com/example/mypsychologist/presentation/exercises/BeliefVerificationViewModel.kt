package com.example.mypsychologist.presentation.exercises

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.R
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.BeliefVerificationEntity
import com.example.mypsychologist.domain.entity.ThoughtDiaryEntity
import com.example.mypsychologist.domain.entity.ThoughtDiaryItemEntity
import com.example.mypsychologist.domain.entity.getMapOfFilledMembers
import com.example.mypsychologist.domain.entity.getMapOfMembers
import com.example.mypsychologist.domain.useCase.ChangeCurrentProblem
import com.example.mypsychologist.domain.useCase.GetBeliefVerificationUseCase
import com.example.mypsychologist.domain.useCase.GetProblemAnalysisUseCase
import com.example.mypsychologist.domain.useCase.GetProblemsUseCase
import com.example.mypsychologist.domain.useCase.SaveBeliefVerificationUseCase
import com.example.mypsychologist.ui.DelegateItem
import com.example.mypsychologist.ui.exercises.cbt.ThoughtDiaryDelegateItem
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class BeliefVerificationViewModel @AssistedInject constructor(
    private val saveBeliefVerificationUseCase: SaveBeliefVerificationUseCase,
    private val getBeliefVerificationUseCase: GetBeliefVerificationUseCase,
    @Assisted("type") private val type: String,
) : ViewModel() {

    private val _screenState: MutableStateFlow<BeliefVerificationScreenState> =
        MutableStateFlow(BeliefVerificationScreenState.Init)
    val screenState: StateFlow<BeliefVerificationScreenState>
        get() = _screenState.asStateFlow()

    private var beliefsVerification = BeliefVerificationEntity()

    init {
        _screenState.value = BeliefVerificationScreenState.Loading
        viewModelScope.launch {
            getSavedVerification()
        }
    }

    private suspend fun getSavedVerification() {
        _screenState.value =
            when (val result = getBeliefVerificationUseCase(type)) {
                is Resource.Success -> {
                    beliefsVerification = result.data

                    beliefsVerification.getMapOfMembers().forEach { (key, value) ->

                        _items = items.map {
                            if (it.content().fieldName == key) ThoughtDiaryDelegateItem(
                                it.content().copy(text = value)
                            ) else it
                        }.toMutableList()
                    }

                    BeliefVerificationScreenState.Data(items)
                }

                is Resource.Loading -> {
                    BeliefVerificationScreenState.Loading
                }

                is Resource.Error -> {
                    BeliefVerificationScreenState.Error(result.msg.toString())
                }
            }
    }

    fun tryToSaveBeliefVerification(type: String) {
        viewModelScope.launch {
            if (fieldsAreCorrect())
                _screenState.value =
                    BeliefVerificationScreenState.RequestResult(
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
                _screenState.value = BeliefVerificationScreenState.ValidationError(items)
        }

        return !containErrors
    }

    private fun markAsNotCorrect(member: String) {
        viewModelScope.launch {
            _items = items.map { item ->
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

    private var _items = listOf(
        ThoughtDiaryDelegateItem(
            ThoughtDiaryItemEntity(
                R.string.truthfulness,
                R.string.truthfulness_hint,
                0,
                ::setTruthfulnessFor,
                BeliefVerificationEntity::truthfulness.name
            )
        ),
        ThoughtDiaryDelegateItem(
            ThoughtDiaryItemEntity(
                R.string.consistency,
                R.string.consistency_hint,
                0,
                ::setConsistencyFor,
                BeliefVerificationEntity::consistency.name
            )
        ),
        ThoughtDiaryDelegateItem(
            ThoughtDiaryItemEntity(
                R.string.benefit,
                R.string.benefit_hint,
                0,
                ::setBenefitFor,
                BeliefVerificationEntity::benefit.name
            )
        ),
    )

    val items
        get() = _items

    private fun BeliefVerificationEntity.mapOfTitles() =
        mapOf(
            ::truthfulness.name to R.string.truthfulness,
            ::consistency.name to R.string.consistency,
            ::benefit.name to R.string.benefit
        )

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("type") type: String
        ): BeliefVerificationViewModel
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