package com.example.mypsychologist.di

import com.example.mypsychologist.ui.psychologist.psychologistWithTasksFragment.PsychologistsWithTasksFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [PsychologistModule::class])
interface PsychologistComponent {

    fun inject(fragment: PsychologistsWithTasksFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): PsychologistComponent
    }
}