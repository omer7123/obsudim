package com.example.mypsychologist.presentation.exercises.newFreeDiaryFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.diaryEntity.NewFreeDiaryWithDateEntity
import com.example.mypsychologist.domain.useCase.freeDiaryUseCase.AddFreeDiaryUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewFreeDiaryViewModel @Inject constructor(
    private val addFreeDiaryUseCase: AddFreeDiaryUseCase,
) :
    ViewModel() {

    private val _screenState: MutableStateFlow<NewFreeDiaryScreenState> =
        MutableStateFlow(NewFreeDiaryScreenState.Init)
    val screenState: StateFlow<NewFreeDiaryScreenState>
        get() = _screenState.asStateFlow()

    fun addDiary(diary: String, date: String? = null) {
        if (diary.isEmpty()) {
            _screenState.value = NewFreeDiaryScreenState.Content
            return
        }

        val resDiary =
            if (date.isNullOrEmpty()) NewFreeDiaryWithDateEntity(diary, "")
            else NewFreeDiaryWithDateEntity(diary, date)

        viewModelScope.launch {
            _screenState.value =
                NewFreeDiaryScreenState.Loading
            when (val result = addFreeDiaryUseCase(resDiary)) {
                is Resource.Error -> _screenState.value =
                    NewFreeDiaryScreenState.Error(result.msg.toString())

                Resource.Loading -> _screenState.value = NewFreeDiaryScreenState.Loading
                is Resource.Success -> _screenState.value = NewFreeDiaryScreenState.Success
            }
        }
    }
}

