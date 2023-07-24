package com.example.mypsychologist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mypsychologist.R
import com.example.mypsychologist.domain.entity.ThoughtDiaryEntity
import com.example.mypsychologist.domain.entity.getMapOfMembers
import com.example.mypsychologist.domain.useCase.SaveThoughtDiaryUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class NewThoughtDiaryViewModel(private val saveThoughtDiaryUseCase: SaveThoughtDiaryUseCase) :
    ViewModel() {

    private val _screenState: MutableStateFlow<NewThoughtDiaryScreenState> =
        MutableStateFlow(NewThoughtDiaryScreenState.Init)
    val screenState: StateFlow<NewThoughtDiaryScreenState>
        get() = _screenState.asStateFlow()

    private var diary = ThoughtDiaryEntity()

    fun setSituation(it: String) {
        diary = diary.copy(situation = it)
    }

    fun setMood(it: String) {
        diary = diary.copy(mood = it)
    }

    fun setLevel(it: Int) {
        diary = diary.copy(level = it)
    }

    fun setAutoThought(it: String) {
        diary = diary.copy(autoThought = it)
    }

    fun setProofs(it: String) {
        diary = diary.copy(proofs = it)
    }

    fun setRefutations(it: String) {
        diary = diary.copy(refutations = it)
    }

    fun setAlternativeThought(it: String) {
        diary = diary.copy(alternativeThought = it)
    }

    fun setNewMood(it: String) {
        diary = diary.copy(newMood = it)
    }

    fun setNewLevel(it: Int) {
        diary = diary.copy(newLevel = it)
    }

    fun tryToSaveDiary() {
        if (fieldsAreCorrect()) {
            _screenState.value = NewThoughtDiaryScreenState.RequestResult(saveThoughtDiaryUseCase(diary))
        }
    }

    private fun fieldsAreCorrect(): Boolean {
        var containErrors = false
        val membersWithError = mutableListOf<String>()

        diary.getMapOfMembers().forEach { (member, value) ->
            if (value.isEmpty()) {
                containErrors = true
                membersWithError.add(member)
            }
        }

        if (containErrors) {
            _screenState.value = NewThoughtDiaryScreenState.ValidationError(membersWithError)
        }

        return !containErrors
    }

    private val titlesWithHints = mapOf(
        R.string.situation to R.string.situation_help,
        R.string.mood to R.string.mood_help,
        R.string.auto_thought to R.string.auto_thought_help,
        R.string.proofs to R.string.proofs_help,
        R.string.refutations to R.string.refutations_help,
        R.string.alternative_thought to R.string.alternative_thought_help,
        R.string.new_mood to R.string.new_mood_help
    )

    fun getHintIdFor(titleId: Int) =
        titlesWithHints[titleId]

    class Factory @Inject constructor(private val saveThoughtDiaryUseCase: SaveThoughtDiaryUseCase) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return NewThoughtDiaryViewModel(saveThoughtDiaryUseCase) as T
        }
    }
}