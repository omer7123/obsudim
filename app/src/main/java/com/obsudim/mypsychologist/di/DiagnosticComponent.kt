package com.obsudim.mypsychologist.di

import com.obsudim.mypsychologist.ui.diagnostics.historyTestFragment.FragmentTestHistory
import com.obsudim.mypsychologist.ui.diagnostics.passingTestFragment.PassingTestFragment
import com.obsudim.mypsychologist.ui.diagnostics.testResultFragment.TestResultFragment
import com.obsudim.mypsychologist.ui.diagnostics.testsFragment.FragmentTests
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [DiagnosticModule::class])
interface DiagnosticComponent {
    fun inject(fragment: FragmentTests)
    fun inject(fragment: FragmentTestHistory)
    fun inject(fragment: PassingTestFragment)
    fun inject(fragment: TestResultFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): DiagnosticComponent
    }
}