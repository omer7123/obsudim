package com.example.mypsychologist.di

import com.example.mypsychologist.ui.education.educationFragment.EducationFragment
import com.example.mypsychologist.ui.education.educationTopicsFragment.EducationTopicsFragment
import dagger.Module
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [EducationModule::class])
interface EducationComponent {

    fun inject(fragment: EducationTopicsFragment)
    fun inject(fragment: EducationFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): EducationComponent
    }
}

@Module
interface EducationModule {

}
