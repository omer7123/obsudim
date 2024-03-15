package com.example.mypsychologist.di

import com.example.mypsychologist.ui.diagnostics.DASSTestFragment
import com.example.mypsychologist.ui.diagnostics.FragmentBeckDepressionTest
import com.example.mypsychologist.ui.diagnostics.FragmentTestHistory
import com.example.mypsychologist.ui.diagnostics.FragmentTests
import com.example.mypsychologist.ui.diagnostics.JASTestFragment
import com.example.mypsychologist.ui.diagnostics.MBITestFragment
import com.example.mypsychologist.ui.diagnostics.SMQTestFragment
import com.example.mypsychologist.ui.diagnostics.STAITestFragment
import com.example.mypsychologist.ui.psychologist.FragmentClientTests
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [DiagnosticModule::class])
interface DiagnosticComponent {
    fun inject(fragment: FragmentBeckDepressionTest)
    fun inject(fragment: FragmentTests)
    fun inject(fragment: FragmentTestHistory)
    fun inject(fragment: FragmentClientTests)
    fun inject(fragment: SMQTestFragment)
    fun inject(fragment: DASSTestFragment)
    fun inject(fragment: STAITestFragment)
    fun inject(fragment: JASTestFragment)
    fun inject(fragment: MBITestFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): DiagnosticComponent
    }
}