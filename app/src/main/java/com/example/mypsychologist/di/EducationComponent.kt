package com.example.mypsychologist.di

import com.example.mypsychologist.ui.education.EducationFragment
import com.example.mypsychologist.ui.education.EducationItemFragment
import com.example.mypsychologist.ui.education.EducationTopicsFragment
import dagger.Module
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [EducationModule::class])
interface EducationComponent {

    fun inject(fragment: EducationTopicsFragment)
    fun inject(fragment: EducationItemFragment)
    fun inject(fragment: EducationFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): EducationComponent
    }
}

@Module
interface EducationModule {

}
