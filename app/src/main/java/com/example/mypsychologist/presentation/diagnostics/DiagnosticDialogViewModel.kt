package com.example.mypsychologist.presentation.diagnostics

import androidx.lifecycle.ViewModel
import com.example.mypsychologist.R

class DiagnosticDialogViewModel : ViewModel() {

    private val testTitleIdToScreenId = mapOf(
        R.string.depression_beck_test to R.id.fragment_beck_depression_test
    )

    fun getScreenIdFor(testTitleId: Int) =
        testTitleIdToScreenId[testTitleId]
}