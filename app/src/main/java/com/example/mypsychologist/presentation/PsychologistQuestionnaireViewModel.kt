package com.example.mypsychologist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.domain.entity.PsychologistData
import com.example.mypsychologist.domain.entity.PsychologistInfo
import com.example.mypsychologist.domain.entity.mapOfValidationMembers
import com.example.mypsychologist.domain.useCase.SavePsychologistUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class PsychologistQuestionnaireViewModel(private val savePsychologistUseCase: SavePsychologistUseCase) :
    ViewModel() {

    private val _screenState: MutableStateFlow<PsychologistFormScreenState> =
        MutableStateFlow(PsychologistFormScreenState.Init)
    val screenState: StateFlow<PsychologistFormScreenState>
        get() = _screenState.asStateFlow()

    private var _info = PsychologistInfo()
    val info: PsychologistInfo
        get() = _info

    fun setName(it: String) {
        _info = info.copy(name = it)
    }

    fun setEducation(it: String) {
        _info = info.copy(education = it)
    }

    fun setAbout(it: String) {
        _info = info.copy(about = it)
    }

    fun addSpecialization(spec: String) {
        viewModelScope.launch {
            val newList = info.specialization.map { it }.toMutableList()
            newList.add(spec)
            _info = info.copy(specialization = newList)
        }
    }

    fun removeSpecialization(spec: String) {
        viewModelScope.launch {
            _info = info.copy(specialization = info.specialization.filter { it != spec })
        }
    }

    fun addCourse(course: String) {
        viewModelScope.launch {
            val newList = info.courses.map { it }.toMutableList()
            newList.add(course)
            _info = info.copy(courses = newList)
        }
    }

    fun removeCourse(course: String) {
        viewModelScope.launch {
            _info = info.copy(courses = info.courses.filter { it != course })
        }
    }

    fun tryToSave() {
        val data = PsychologistData(info, listOf())
        if (fieldsAreCorrect(data.info)) {
            viewModelScope.launch {
                _screenState.value =
                    PsychologistFormScreenState.RequestResult(savePsychologistUseCase(data))
            }
        }
    }

    private fun fieldsAreCorrect(info: PsychologistInfo): Boolean {
        var containErrors = false
        val membersWithError = mutableListOf<String>()

        info.mapOfValidationMembers().forEach { (member, value) ->
            if (value.isEmpty()) {
                containErrors = true
                membersWithError.add(member)
            }
        }

        if (containErrors)
            _screenState.value = PsychologistFormScreenState.ValidationError(membersWithError)

        return !containErrors
    }

    class Factory @Inject constructor(private val savePsychologistUseCase: SavePsychologistUseCase) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return PsychologistQuestionnaireViewModel(savePsychologistUseCase) as T
        }
    }
}