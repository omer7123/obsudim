package com.example.mypsychologist.di

import com.example.mypsychologist.ui.diagnostics.historyTestFragment.FragmentTestHistory
import com.example.mypsychologist.ui.diagnostics.passingTestFragment.PassingTestFragment
import com.example.mypsychologist.ui.diagnostics.testResultFragment.TestResultFragment
import com.example.mypsychologist.ui.diagnostics.testsFragment.FragmentTests
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