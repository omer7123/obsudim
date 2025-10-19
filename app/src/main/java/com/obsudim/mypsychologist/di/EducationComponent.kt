package com.obsudim.mypsychologist.di

import com.obsudim.mypsychologist.ui.education.educationFragment.EducationFragment
import com.obsudim.mypsychologist.ui.education.educationTopicsFragment.EducationTopicsFragment
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
