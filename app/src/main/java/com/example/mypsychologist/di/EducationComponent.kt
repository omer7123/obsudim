package com.example.mypsychologist.di

import com.example.mypsychologist.ui.education.EducationFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [EducationModule::class])
interface EducationComponent {

    fun inject(fragment: EducationFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): EducationComponent
    }
}