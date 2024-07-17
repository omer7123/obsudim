package com.example.mypsychologist.presentation.diagnostics

import androidx.lifecycle.ViewModel
import com.example.mypsychologist.R

class DiagnosticDialogViewModel : ViewModel() {

    private val testTitleIdToScreenId = mapOf(
        R.string.depression_beck_test to R.id.fragment_beck_depression_test,
        R.string.cmq to R.id.SMQ_test_fragment,
        R.string.dass21 to R.id.DASS_test_fragment,
        R.string.stai to R.id.STAI_test_fragment,
        R.string.jas to R.id.JASTestFragment,
        R.string.mbi to R.id.MBITestFragment,
        R.string.CSI to R.id.CSITestFragment,
    )

    private fun indexOfTest(testTitleId: String): Int =
        when (testTitleId) {
            "7921dc6c-0933-45b3-a97c-aeb5ef76c865" -> R.string.dass21
            "9a1a674f-246f-4e80-96c6-e0cc5f5d148a" -> R.string.CSI
            "7bdebf8d-dfc5-417a-b583-d8364baed8d5" -> R.string.stai
            "d4c551a7-f5ea-4688-bbae-5b1cac3bb394" -> R.string.cmq
            "0923a7a8-510c-41d7-a273-3c7bd5481ba3" -> R.string.jas
            "d00d0df9-afc4-4c28-bc4e-963d9a643986" -> R.string.depression_beck_test
            "be28c8c4-18e9-4c2b-a3de-3b73dc50d929" -> R.string.mbi
            else -> {
                R.string.dass21
            }
        }

    fun getScreenIdFor(testTitleId: String): Int? {
        val index = indexOfTest(testTitleId)
        return testTitleIdToScreenId[index]
    }

}