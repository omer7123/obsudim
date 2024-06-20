package com.example.mypsychologist.presentation.exercises

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.FreeDiary
import com.example.mypsychologist.domain.useCase.retrofitUseCase.freeDiaryUseCase.GetFreeDiaryListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FreeDiariesViewModel @Inject constructor(
    private val getFreeDiaryListUseCase: GetFreeDiaryListUseCase,
) : ViewModel() {

    private val _screenState: MutableStateFlow<ThoughtDiariesScreenState> =
        MutableStateFlow(ThoughtDiariesScreenState.Init)

    val screenState: StateFlow<ThoughtDiariesScreenState>
        get() = _screenState.asStateFlow()

    fun loadDiaries() {
        _screenState.value = ThoughtDiariesScreenState.Loading

        viewModelScope.launch {
            when (val result = getFreeDiaryListUseCase()) {
                is Resource.Error -> _screenState.value = ThoughtDiariesScreenState.Error
                Resource.Loading -> _screenState.value = ThoughtDiariesScreenState.Loading
                is Resource.Success -> {
                    val hashMap = convertListToHashMap(result.data)
                    Log.e("Ololo", hashMap.toString())
                    Log.e("Result", result.data.toString())
                    _screenState.value = ThoughtDiariesScreenState.Data(hashMap)
                }
            }
        }
    }
    private fun convertListToHashMap(list: List<FreeDiary>): HashMap<String, String> {
        val hashMap = HashMap<String, String>()
        for (item in list) {
            hashMap[item.id] = item.text
        }
        return hashMap
    }
}