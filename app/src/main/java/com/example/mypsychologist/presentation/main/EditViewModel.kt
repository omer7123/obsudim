package com.example.mypsychologist.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.R
import com.example.mypsychologist.domain.entity.ClientInfoEntity
import com.example.mypsychologist.domain.entity.InputItemEntity
import com.example.mypsychologist.domain.entity.TagEntity
import com.example.mypsychologist.domain.entity.getMapOfMembers
import com.example.mypsychologist.domain.useCase.*
import com.example.mypsychologist.ui.DelegateItem
import com.example.mypsychologist.ui.exercises.cbt.InputDelegateItem
import com.example.mypsychologist.ui.exercises.cbt.IntDelegateItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class EditViewModel(
    private val saveClientInfoUseCase: SaveClientInfoUseCase,
    private val changePasswordUseCase: ChangePasswordUseCase,
    private val changePhoneUseCase: ChangePhoneUseCase,
    private val getClientDataUseCase: GetClientDataUseCase
) : ViewModel() {

    private val _screenState: MutableStateFlow<EditScreenState> =
        MutableStateFlow(EditScreenState.Init)
    val screenState: StateFlow<EditScreenState>
        get() = _screenState.asStateFlow()

    private var info = ClientInfoEntity()

    private fun setBirthday(date: String) {
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

    fun tryToSaveInfo() {
        viewModelScope.launch(Dispatchers.IO) {
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
            _screenState.value =  EditScreenState.ValidationError(items)

        return !containErrors
    }


    private fun markAsNotCorrect(member: String) {
        viewModelScope.launch {
            items = items.map { item ->
                if (item is InputDelegateItem &&
                    item.content().titleId == info.mapOfTitles()[member]
                )
                    InputDelegateItem(item.content().copy(isNotCorrect = true))
                else
                    item
            }
        }
    }

    var items = listOf<DelegateItem>(
        InputDelegateItem(
            InputItemEntity(
                R.string.name,
                saveFunction = ::setName
            )
        ),
        InputDelegateItem(
            InputItemEntity(
                R.string.birthday,
                saveFunction = ::setBirthday
            )
        ),
        IntDelegateItem(R.string.level),
        InputDelegateItem(
            InputItemEntity(
                R.string.gender,
                saveFunction = ::setGender
            )
        ),
        InputDelegateItem(
            InputItemEntity(
                R.string.city,
                saveFunction = ::setCity
            )
        )
    )

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
        private val changePhoneUseCase: ChangePhoneUseCase,
        private val getClientDataUseCase: GetClientDataUseCase
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return EditViewModel(
                saveClientInfoUseCase, changePasswordUseCase, changePhoneUseCase, getClientDataUseCase
            ) as T
        }
    }
}