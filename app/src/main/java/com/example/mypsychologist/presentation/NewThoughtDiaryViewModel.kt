package com.example.mypsychologist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.R
import com.example.mypsychologist.domain.entity.ThoughtDiaryEntity
import com.example.mypsychologist.domain.entity.ThoughtDiaryItemEntity
import com.example.mypsychologist.domain.entity.getMapOfMembers
import com.example.mypsychologist.domain.useCase.SaveThoughtDiaryUseCase
import com.example.mypsychologist.ui.DelegateItem
import com.example.mypsychologist.ui.exercises.cbt.IntDelegateItem
import com.example.mypsychologist.ui.exercises.cbt.ThoughtDiaryDelegateItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewThoughtDiaryViewModel(private val saveThoughtDiaryUseCase: SaveThoughtDiaryUseCase) :
    ViewModel() {

    private val _screenState: MutableStateFlow<NewThoughtDiaryScreenState> =
        MutableStateFlow(NewThoughtDiaryScreenState.Init)
    val screenState: StateFlow<NewThoughtDiaryScreenState>
        get() = _screenState.asStateFlow()

    private var diary = ThoughtDiaryEntity()

    private fun setSituation(it: String) {
        diary = diary.copy(situation = it)
    }

    private fun setMood(it: String) {
        diary = diary.copy(mood = it)
    }

    fun setLevel(titleId: Int, it: Int) {
        when (titleId) {
            R.string.level ->
                diary = diary.copy(level = it)
            R.string.new_level ->
                diary = diary.copy(newLevel = it)
        }
    }

    private fun setAutoThought(it: String) {
        diary = diary.copy(autoThought = it)
    }

    private fun setProofs(it: String) {
        diary = diary.copy(proofs = it)
    }

    private fun setRefutations(it: String) {
        diary = diary.copy(refutations = it)
    }

    private fun setAlternativeThought(it: String) {
        diary = diary.copy(alternativeThought = it)
    }

    private fun setNewMood(it: String) {
        diary = diary.copy(newMood = it)
    }

    fun tryToSaveDiary() {
        if (fieldsAreCorrect()) {
            _screenState.value =
                NewThoughtDiaryScreenState.RequestResult(saveThoughtDiaryUseCase(diary))
        }
    }

    private fun fieldsAreCorrect(): Boolean {
        var containErrors = false

        diary.getMapOfMembers().forEach { (member, value) ->

            if (value.isEmpty()) {
                containErrors = true

                markAsNotCorrect(member)
            }
        }

        if (containErrors)
            _screenState.value = NewThoughtDiaryScreenState.ValidationError(items)

        return !containErrors
    }

    private fun markAsNotCorrect(member: String) {
        viewModelScope.launch {
            items = items.map { item ->
                if (item is ThoughtDiaryDelegateItem &&
                    item.content().titleId == diary.mapOfTitles()[member]
                )
                    ThoughtDiaryDelegateItem(item.content().copy(isNotCorrect = true))
                else
                    item
            }
        }
    }

    var items = listOf<DelegateItem>(
        ThoughtDiaryDelegateItem(
            ThoughtDiaryItemEntity(
                R.string.situation,
                R.string.situation_helper,
                R.string.situation_help,
                ::setSituation
            )
        ),
        ThoughtDiaryDelegateItem(
            ThoughtDiaryItemEntity(
                R.string.mood,
                R.string.mood_helper,
                R.string.mood_help,
                ::setMood
            )
        ),
        IntDelegateItem(R.string.level),
        ThoughtDiaryDelegateItem(
            ThoughtDiaryItemEntity(
                R.string.auto_thought,
                R.string.auto_thought_helper,
                R.string.auto_thought_help,
                ::setAutoThought
            )
        ),
        ThoughtDiaryDelegateItem(
            ThoughtDiaryItemEntity(
                R.string.proofs,
                R.string.proofs_helper,
                R.string.proofs_help,
                ::setProofs
            )
        ),
        ThoughtDiaryDelegateItem(
            ThoughtDiaryItemEntity(
                R.string.refutations,
                R.string.refutations_helper,
                R.string.refutations_help,
                ::setRefutations
            )
        ),
        ThoughtDiaryDelegateItem(
            ThoughtDiaryItemEntity(
                R.string.alternative_thought,
                R.string.alternative_thought_helper,
                R.string.alternative_thought_help,
                ::setAlternativeThought
            )
        ),
        ThoughtDiaryDelegateItem(
            ThoughtDiaryItemEntity(
                R.string.new_mood,
                R.string.new_mood_helper,
                R.string.new_mood_help,
                ::setNewMood
            )
        ),
        IntDelegateItem(R.string.new_level)
    )
        private set

    class Factory @Inject constructor(private val saveThoughtDiaryUseCase: SaveThoughtDiaryUseCase) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return NewThoughtDiaryViewModel(saveThoughtDiaryUseCase) as T
        }
    }

    private fun ThoughtDiaryEntity.mapOfTitles() =
        mapOf(
            ::situation.name to R.string.situation,
            ::mood.name to R.string.mood,
            ::level.name to R.string.level.toString(),
            ::autoThought.name to R.string.auto_thought,
            ::proofs.name to R.string.proofs,
            ::refutations.name to R.string.refutations,
            ::alternativeThought.name to R.string.alternative_thought,
            ::newMood.name to R.string.new_mood,
            ::newLevel.name to R.string.new_level.toString()
        )
}