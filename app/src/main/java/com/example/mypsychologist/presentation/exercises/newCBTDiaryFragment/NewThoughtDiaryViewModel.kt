package com.example.mypsychologist.presentation.exercises.newCBTDiaryFragment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.R
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.ThoughtDiaryEntity
import com.example.mypsychologist.domain.entity.ThoughtDiaryItemEntity
import com.example.mypsychologist.domain.entity.exerciseEntity.DailyTaskMarkIdEntity
import com.example.mypsychologist.domain.entity.exerciseEntity.ExerciseResultEntity
import com.example.mypsychologist.domain.entity.getMapOfMembers
import com.example.mypsychologist.domain.useCase.exerciseUseCases.GetExerciseDetailUseCase
import com.example.mypsychologist.domain.useCase.exerciseUseCases.MarkAsCompleteExerciseUseCase
import com.example.mypsychologist.domain.useCase.exerciseUseCases.SaveCBTDiaryUseCase
import com.example.mypsychologist.presentation.exercises.exercisesFragment.SaveExerciseStatus
import com.example.mypsychologist.ui.core.delegateItems.DelegateItem
import com.example.mypsychologist.ui.exercises.newCbtDiaryFragment.SliderDelegateItem
import com.example.mypsychologist.ui.exercises.newCbtDiaryFragment.ThoughtDiaryDelegateItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewThoughtDiaryViewModel(
    private val saveCBTDiaryUseCase: SaveCBTDiaryUseCase,
    private val getExerciseDetailUseCase: GetExerciseDetailUseCase,
    private val markAsCompleteExerciseUseCase: MarkAsCompleteExerciseUseCase
) :
    ViewModel() {

    private val _screenState: MutableStateFlow<NewThoughtDiaryScreenState> =
        MutableStateFlow(NewThoughtDiaryScreenState.Init)
    val screenState: StateFlow<NewThoughtDiaryScreenState>
        get() = _screenState.asStateFlow()

    private val _saveStatus: MutableStateFlow<SaveExerciseStatus> =
        MutableStateFlow(SaveExerciseStatus.Init)
    val saveStatus: StateFlow<SaveExerciseStatus>
        get() = _saveStatus.asStateFlow()

    private val resultExercise = ArrayList<ExerciseResultEntity>()

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

    fun tryToSaveDiary(taskId: String) {
        viewModelScope.launch {
            if (fieldsAreCorrect()) {
                saveCBTDiaryUseCase(diary).collect { resource ->
                    when (resource) {
                        is Resource.Error -> _saveStatus.value =
                            SaveExerciseStatus.Error(resource.msg.toString())

                        Resource.Loading -> Unit
                        is Resource.Success -> {
                            if (taskId != "") {
                                markAsCompleteExerciseUseCase(DailyTaskMarkIdEntity(taskId)).collect {
                                    when (it) {
                                        is Resource.Error -> _saveStatus.value =
                                            SaveExerciseStatus.Error(it.toString())

                                        Resource.Loading -> Unit
                                        is Resource.Success -> _saveStatus.value =
                                            SaveExerciseStatus.Success
                                    }
                                }
                            } else {
                                _saveStatus.value =
                                    SaveExerciseStatus.Success
                            }
                        }
                    }
                }
            }
        }
    }

    private fun fieldsAreCorrect(): Boolean {
        var containErrors = false

        diary.getMapOfMembers().forEach { (member, value) ->
            Log.e(member, value)
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

/*    fun tryToSave(exerciseId: String, taskId: String) {
        val currentState = _screenState.value

        if (currentState is NewExerciseScreenState.Content) {
            val updatesFields = currentState.data.fields.map { delegateItem ->
                when (delegateItem) {
                    is InputExerciseDelegateItem -> {
                        val updatedContent =
                            if (resultExercise.find { it.fieldId == delegateItem.content().id }?.value == "") {
                                delegateItem.content().copy(isNotCorrect = true)
                            } else {
                                delegateItem.content().copy(isNotCorrect = false)
                            }
                        val updateDelegateItem = InputExerciseDelegateItem(
                            updatedContent
                        )

                        updateDelegateItem
                    }

                    else -> delegateItem
                }
            }

            val hasErrors = updatesFields.any {
                it is InputExerciseDelegateItem && it.content().isNotCorrect
            }
            if (hasErrors) {
                _screenState.value =
                    currentState.copy(data = currentState.data.copy(fields = updatesFields))
            } else {
                viewModelScope.launch {
                    saveCBTDiaryUseCase(
                        diary
                    ).collect { resource ->
                        when (resource) {
                            is Resource.Error -> _saveStatus.value =
                                SaveExerciseStatus.Error(resource.msg.toString())

                            Resource.Loading -> Unit
                            is Resource.Success -> {
                                if (taskId != "") {
                                    markAsCompleteExerciseUseCase(DailyTaskMarkIdEntity(taskId)).collect {
                                        when (it) {
                                            is Resource.Error -> _saveStatus.value =
                                                SaveExerciseStatus.Error(it.toString())

                                            Resource.Loading -> Unit
                                            is Resource.Success -> _saveStatus.value =
                                                SaveExerciseStatus.Success
                                        }
                                    }
                                } else {
                                    _saveStatus.value =
                                        SaveExerciseStatus.Success
                                }
                            }
                        }
                    }
                }
            }
        }
    } */

/*    private fun saveTextInput(res: ExerciseResultEntity) {
        val searchItem = resultExercise.find { it.fieldId == res.fieldId }
        searchItem!!.value = res.value

        val currentState = _screenState.value
        if (currentState is NewExerciseScreenState.Content) {
            val updatesFields = currentState.data.fields.map { delegateItem ->
                when (delegateItem) {
                    is InputExerciseDelegateItem -> {
                        if (delegateItem.content().id == res.fieldId) {
                            delegateItem.content().text = res.value
                        } else if (delegateItem.content().text.isNotEmpty() && delegateItem.content().isNotCorrect) {
                            delegateItem.content().isNotCorrect = false
                        } else {
                            delegateItem.content()
                        }
                        delegateItem
                    }

                    else -> delegateItem
                }
            }
            _screenState.value =
                currentState.copy(data = currentState.data.copy(fields = updatesFields))
        }
    }

    private fun ExerciseDetailEntity.toDelegateItems(): ExerciseDetailWithDelegateItem {
        return ExerciseDetailWithDelegateItem(id = id, title = title, description = description,
            fields = fields.map { field ->
                when (field.type) {
                    TypeOfExercise.TextInput -> InputExerciseDelegateItem(
                        InputItemExerciseEntity(
                            id = field.id,
                            titleId = field.title,
                            helperId = field.description,
                            hintId = field.title,
                            saveFunction = { res -> saveTextInput(res) }
                        )
                    )

                    TypeOfExercise.NumberInput -> IntDelegateItem(
                        InputIntExerciseEntity(
                            id = field.id,
                            title = field.title,
                            value = "50",
                            saveResult = { res -> saveTextInput(res) }
                        )
                    )
                }
            }
        )
    }*/

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
        SliderDelegateItem(R.string.level),
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
        SliderDelegateItem(R.string.new_level)
    )
        private set

    class Factory @Inject constructor(
        private val saveCBTDiaryUseCase: SaveCBTDiaryUseCase,
        private val getExerciseDetailUseCase: GetExerciseDetailUseCase,
        private val markAsCompleteExerciseUseCase: MarkAsCompleteExerciseUseCase
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return NewThoughtDiaryViewModel(
                saveCBTDiaryUseCase,
                getExerciseDetailUseCase,
                markAsCompleteExerciseUseCase
            ) as T
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