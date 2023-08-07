package com.example.mypsychologist.di

import com.example.mypsychologist.ui.psychologist.PsychologistFragment
import com.example.mypsychologist.ui.psychologist.PsychologistsFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [PsychologistModule::class])
interface PsychologistComponent {

    fun inject(fragment: PsychologistsFragment)
    fun inject(fragment: PsychologistFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): PsychologistComponent
    }
}