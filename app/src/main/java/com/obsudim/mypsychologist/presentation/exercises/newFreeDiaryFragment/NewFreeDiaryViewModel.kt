package com.obsudim.mypsychologist.presentation.exercises.newFreeDiaryFragment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.domain.entity.ModelDepressionPostReqEntity
import com.obsudim.mypsychologist.domain.entity.ModelDepressionRespEntity
import com.obsudim.mypsychologist.domain.entity.diaryEntity.NewFreeDiaryWithDateEntity
import com.obsudim.mypsychologist.domain.useCase.freeDiaryUseCase.AddFreeDiaryUseCase
import com.obsudim.mypsychologist.domain.useCase.modelDepressionUseCases.SaveNoteModelUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewFreeDiaryViewModel @Inject constructor(
    private val addFreeDiaryUseCase: AddFreeDiaryUseCase,
    private val saveNoteModelUseCase: SaveNoteModelUseCase
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
                is Resource.Success -> {
                    startModel(diary)
                    delay(10000)
                    _screenState.value = NewFreeDiaryScreenState.Success
                }
            }
        }
    }

    private suspend fun startModel(text: String){
        saveNoteModelUseCase(ModelDepressionPostReqEntity(text)).collect { state->
            when(state){
                is Resource.Error<ModelDepressionRespEntity> -> {
                    Log.e("error", state.msg.toString())
                    _screenState.value = NewFreeDiaryScreenState.Error("Произошла непредвиденная ошибка")
                }
                Resource.Loading -> {
                    _screenState.value = NewFreeDiaryScreenState.LoadingModel
                }
                is Resource.Success<ModelDepressionRespEntity> -> {
                    val resMsg = when(state.data.prediction){
                        0 -> "Склонность к депрессии не выявлена"
                        1 -> "Кажется, вы склонны к депрессии"
                        else -> ""
                    }
                    _screenState.value = NewFreeDiaryScreenState.SuccessModel(resMsg)
                }
            }
        }
    }
}

