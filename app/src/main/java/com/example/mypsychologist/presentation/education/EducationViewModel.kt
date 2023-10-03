package com.example.mypsychologist.presentation.education

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.domain.useCase.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class EducationViewModel(
    private val getEducationMaterialUseCase: GetEducationMaterialUseCase,
    private val saveProgressUseCase: SaveProgressUseCase
) : ViewModel() {

    fun getMaterial(topic: GetEducationMaterialUseCase.Topic): List<String> =
        getEducationMaterialUseCase(topic)

    fun saveProgress(item: Int, topicTag: String) {
        viewModelScope.launch(Dispatchers.IO) {
            saveProgressUseCase(topicTag, item)
        }
    }

    class Factory @Inject constructor(
        private val getEducationMaterialUseCase: GetEducationMaterialUseCase,
        private val saveProgressUseCase: SaveProgressUseCase
    ) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return EducationViewModel(
                getEducationMaterialUseCase, saveProgressUseCase
            ) as T
        }
    }
}