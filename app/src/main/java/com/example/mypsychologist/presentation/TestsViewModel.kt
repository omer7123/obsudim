package com.example.mypsychologist.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.R
import com.example.mypsychologist.domain.entity.TestCardEntity
import com.example.mypsychologist.domain.entity.TestGroupEntity
import com.example.mypsychologist.ui.DelegateItem
import com.example.mypsychologist.ui.diagnostics.TestDelegateItem
import com.example.mypsychologist.ui.diagnostics.TestGroupDelegateItem
import com.example.mypsychologist.ui.diagnostics.toDelegateItem
import com.example.mypsychologist.ui.diagnostics.toDelegateItems
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TestsViewModel : ViewModel() {

    private val testsWithCategories = mapOf(
        TestGroupEntity(R.string.depression, R.drawable.ic_sentiment_very_dissatisfied) to listOf(
            TestCardEntity(
                R.string.depression_beck_test,
                R.string.depression_beck_test_desc_short,
                R.string.depression_beck_test_desc
            )
        )
    )

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
                (testsWithCategories[category] ?: listOf()).toDelegateItems()
            )
            _screenState.emit(newList)
        }
    }
}