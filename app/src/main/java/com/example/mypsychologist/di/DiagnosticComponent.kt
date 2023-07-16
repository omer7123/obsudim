package com.example.mypsychologist.di

import com.example.mypsychologist.ui.diagnostics.FragmentBeckDepressionTest
import com.example.mypsychologist.ui.diagnostics.FragmentTestHistory
import com.example.mypsychologist.ui.diagnostics.FragmentTests
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [DiagnosticModule::class])
interface DiagnosticComponent {
    fun inject(fragment: FragmentBeckDepressionTest)
    fun inject(fragment: FragmentTests)
    fun inject(fragment: FragmentTestHistory)

    @Subcomponent.Factory
    interface Factory {
        fun create(): DiagnosticComponent
    }
}