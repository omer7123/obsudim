package com.example.mypsychologist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.domain.entity.TestGroupEntity
import com.example.mypsychologist.domain.useCase.TestsWithGroupsUseCase
import com.example.mypsychologist.ui.DelegateItem
import com.example.mypsychologist.ui.diagnostics.TestDelegateItem
import com.example.mypsychologist.ui.diagnostics.TestGroupDelegateItem
import com.example.mypsychologist.ui.diagnostics.toDelegateItem
import com.example.mypsychologist.ui.diagnostics.toDelegateItems
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class TestsViewModel(
    testsWithGroupsUseCase: TestsWithGroupsUseCase
) : ViewModel() {

    private val testsWithCategories = testsWithGroupsUseCase()

    private val _screenState: MutableStateFlow<List<DelegateItem>> =
        MutableStateFlow(getCategories().toDelegateItem())
    val screenState: StateFlow<List<DelegateItem>> =
        _screenState.asStateFlow()

    private fun getCategories(): List<TestGroupEntity> = run {
        testsWithCategories.keys.toList()
    }

    fun setupTestsFor(category: TestGroupEntity) {
        viewModelScope.launch {
            val newList = screenState.value.map { it }.toMutableList()

            val position =
                newList.indexOf(newList.find {
                    (it.content() is TestGroupEntity) &&
                            (it.content() as TestGroupEntity).titleId == category.titleId
                })

            newList.addAll(
                position + 1,
                (testsWithCategories[category] ?: listOf()).toDelegateItems(category.titleId)
            )
            _screenState.value = newList
        }
    }

    fun hintTestsFor(groupTitleId: Int) {
        viewModelScope.launch {

            val newList = screenState.value.filter {
                it is TestGroupDelegateItem ||
                        (it is TestDelegateItem && it.parentGroupTitleId != groupTitleId)
            }

            _screenState.value = newList
        }
    }

    class Factory @Inject constructor(private val testsWithGroupsUseCase: TestsWithGroupsUseCase) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return TestsViewModel(testsWithGroupsUseCase) as T
        }
    }
}