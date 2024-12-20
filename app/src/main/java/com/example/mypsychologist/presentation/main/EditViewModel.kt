package com.example.mypsychologist.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.R
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.ClientInfoEntity
import com.example.mypsychologist.domain.entity.InputItemEntity
import com.example.mypsychologist.domain.entity.TagEntity
import com.example.mypsychologist.domain.entity.getMapOfMembers
import com.example.mypsychologist.domain.useCase.ChangePasswordUseCase
import com.example.mypsychologist.domain.useCase.profile.GetOwnDataUseCase
import com.example.mypsychologist.domain.useCase.profile.SaveClientInfoUseCase
import com.example.mypsychologist.ui.exercises.cbt.InputDelegateItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class EditViewModel(
    private val saveClientInfoUseCase: SaveClientInfoUseCase,
    private val changePasswordUseCase: ChangePasswordUseCase,
    private val getOwnDataUseCase: GetOwnDataUseCase
) : ViewModel() {

    private val _screenState: MutableStateFlow<EditScreenState> =
        MutableStateFlow(EditScreenState.Init)
    val screenState: StateFlow<EditScreenState>
        get() = _screenState.asStateFlow()

    private var info = ClientInfoEntity()

    init {
        _screenState.value = EditScreenState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            getSavedInfo()
        }
    }

    private suspend fun getSavedInfo() {
        _screenState.value =
            when (val result = getOwnDataUseCase()) {
                is Resource.Success -> {
                    info = result.data

                    info.getMapOfMembers().forEach { (key, value) ->

                        _items = items.map {
                            if (it.content().fieldName == key) {

                                InputDelegateItem(
                                    it.content().copy(text = value)
                                )

                            }
                            else
                                it
                        }.toMutableList()
                    }

                    EditScreenState.CurrentData(items, result.data.birthday, result.data.request)
                }

                is Resource.Loading -> {
                    EditScreenState.Loading
                }

                is Resource.Error -> {
                    EditScreenState.Error(result.msg.toString())
                }
            }
    }

    fun setBirthday(date: String) {
        info = info.copy(birthday = date)
    }

    private fun setCity(it: String) {
        info = info.copy(city = it)
    }

    private fun setGender(it: String) {
        info = info.copy(gender = it)
    }

    private fun setName(it: String) {
        info = info.copy(name = it)
    }

    fun setRequest(new: List<TagEntity>) {
        info = info.copy(request = new)
    }

    fun tryToSaveInfo(name: String, birthDate: String) {
        viewModelScope.launch {
            saveClientInfoUseCase(ClientInfoEntity(name, birthday = birthDate))
            if (fieldsAreCorrect())
                _screenState.value =
                    EditScreenState.Response(saveClientInfoUseCase(info))
        }
    }

    private fun fieldsAreCorrect(): Boolean {
        var containErrors = false

        info.getMapOfMembers().forEach { (member, value) ->
            if (value.isEmpty()) {
                containErrors = true

                markAsNotCorrect(member)
            }
        }

        if (containErrors)
            _screenState.value = EditScreenState.ValidationError(items)

        return !containErrors
    }

    private fun markAsNotCorrect(member: String) {
        viewModelScope.launch {
            _items = items.map { item ->
                if (item.content().titleId == info.mapOfTitles()[member])
                    InputDelegateItem(item.content().copy(isNotCorrect = true))
                else
                    item
            }
        }
    }

    private var _items = listOf(
        InputDelegateItem(
            InputItemEntity(
                R.string.name,
                saveFunction = ::setName,
                fieldName = ClientInfoEntity::name.name
            )
        ),
        InputDelegateItem(
            InputItemEntity(
                R.string.gender,
                saveFunction = ::setGender,
                fieldName = ClientInfoEntity::gender.name
            )
        ),
        InputDelegateItem(
            InputItemEntity(
                R.string.city,
                saveFunction = ::setCity,
                fieldName = ClientInfoEntity::city.name
            )
        )
    )

    val items
        get() = _items

    private fun ClientInfoEntity.mapOfTitles() =
        mapOf(
            ::name.name to R.string.name,
            ::birthday.name to R.string.birthday,
            ::gender.name to R.string.gender,
            ::city.name to R.string.city
        )

    class Factory @Inject constructor(
        private val saveClientInfoUseCase: SaveClientInfoUseCase,
        private val changePasswordUseCase: ChangePasswordUseCase,
        private val getOwnDataUseCase: GetOwnDataUseCase
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return EditViewModel(
                saveClientInfoUseCase, changePasswordUseCase, getOwnDataUseCase
            ) as T
        }
    }
}