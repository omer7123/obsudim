package com.example.mypsychologist.di

import com.example.mypsychologist.ui.main.MainFragment
import com.example.mypsychologist.ui.psychologist.*
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [PsychologistModule::class])
interface PsychologistComponent {

    fun inject(fragment: PsychologistsFragment)
    fun inject(fragment: PsychologistFragment)
    fun inject(fragment: RequestToPsychologistFragment)
    fun inject(fragment: PsychologistCabinetFragment)
    fun inject(fragment: ClientRequestFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): PsychologistComponent
    }
}