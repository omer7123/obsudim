package com.example.mypsychologist.presentation.exercises

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.useCase.CheckIfPsychologistUseCase
import com.example.mypsychologist.domain.useCase.GetClientFreeDiariesUseCase
import com.example.mypsychologist.domain.useCase.GetFreeDiariesUseCase
import com.example.mypsychologist.domain.useCase.retrofitUseCase.freeDiaryUseCase.AddFreeDiaryUseCase
import com.example.mypsychologist.domain.useCase.retrofitUseCase.freeDiaryUseCase.GetFreeDiaryListUseCase
import com.example.mypsychologist.presentation.main.mainFragment.MainViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
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
    private fun convertListToHashMap(list: List<String>): HashMap<String, String> {
        val hashMap = HashMap<String, String>()
        for (item in list) {
            hashMap[item] = item
        }
        return hashMap
    }
}