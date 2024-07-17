package com.example.mypsychologist.presentation.diagnostics

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.diagnosticEntity.TestEntity
import com.example.mypsychologist.domain.useCase.TestsWithGroupsUseCase
import com.example.mypsychologist.domain.useCase.retrofitUseCase.diagnosticsUseCases.GetAllTestsUseCase
import com.example.mypsychologist.ui.DelegateItem
import com.example.mypsychologist.ui.diagnostics.TestWithoutCategoryDelegateItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class TestsViewModel(
    testsWithGroupsUseCase: TestsWithGroupsUseCase,
    private val getAllTestsUseCase: GetAllTestsUseCase
) : ViewModel() {

    private val testsWithCategories = testsWithGroupsUseCase()


    private val _screenState: MutableStateFlow<TestsScreenState> =
        MutableStateFlow(TestsScreenState.Initial)

    val screenState: StateFlow<TestsScreenState> = _screenState.asStateFlow()

    fun getTests() {
        _screenState.value = TestsScreenState.Loading
        viewModelScope.launch {
            when (val result = getAllTestsUseCase()) {
                is Resource.Success -> {
                    _screenState.value = TestsScreenState.Content(
                        convertTestListToDelegateItems(
                            result.data
                        )
                    )
                }

                is Resource.Error -> _screenState.value =
                    TestsScreenState.Error(result.msg.toString())

                Resource.Loading -> {}
            }
        }
    }

    private fun convertTestListToDelegateItems(tests: List<TestEntity>): List<DelegateItem> {
        return tests.map { test ->
            TestWithoutCategoryDelegateItem(test)
        }
    }

//    private fun getCategories(): List<TestGroupEntity> = run {
//        testsWithCategories.keys.toList()
//    }
//
//    fun setupTestsFor(category: TestGroupEntity) {
//        viewModelScope.launch {
//            val newList = screenState.value.map { it }.toMutableList()
//
//            val position =
//                newList.indexOf(newList.find {
//                    (it.content() is TestGroupEntity) &&
//                            (it.content() as TestGroupEntity).titleId == category.titleId
//                })
//
//            newList.addAll(
//                position + 1,
//                (testsWithCategories[category] ?: listOf()).toDelegateItems(category.titleId)
//            )
//            _screenState.value = newList
//        }
//
//    }

//    fun hintTestsFor(groupTitleId: Int) {
//        viewModelScope.launch {
//
//            val newList = screenState.value.filter {
//                it is TestGroupDelegateItem ||
//                        (it is TestDelegateItem && it.parentGroupTitleId != groupTitleId)
//            }
//
//            _screenState.value = newList
//        }
//    }


    class Factory @Inject constructor(
        private val testsWithGroupsUseCase: TestsWithGroupsUseCase,
        private val getAllTestsUseCase: GetAllTestsUseCase
    ) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return TestsViewModel(testsWithGroupsUseCase, getAllTestsUseCase) as T
        }
    }
}