package com.example.mypsychologist.presentation.diagnostics

import androidx.lifecycle.ViewModel
import com.example.mypsychologist.R

class DiagnosticDialogViewModel : ViewModel() {

    private val testTitleIdToScreenId = mapOf(
        R.string.depression_beck_test to R.id.fragment_beck_depression_test,
        R.string.cmq to R.id.SMQ_test_fragment,
        R.string.dass21 to R.id.DASS_test_fragment,
        R.string.stai to R.id.STAI_test_fragment
    )

    fun getScreenIdFor(testTitleId: Int) =
        testTitleIdToScreenId[testTitleId]
}