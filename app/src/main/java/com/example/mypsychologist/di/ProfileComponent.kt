package com.example.mypsychologist.di

import com.example.mypsychologist.ui.main.EditFragment
import com.example.mypsychologist.ui.main.FeedbackFragment
import com.example.mypsychologist.ui.main.MainFragment
import com.example.mypsychologist.ui.main.ProfileFragment
import com.example.mypsychologist.ui.psychologist.RequestToPsychologistFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [ProfileModule::class])
interface ProfileComponent {

    fun inject(fragment: MainFragment)
    fun inject(fragment: ProfileFragment)
    fun inject(fragment: FeedbackFragment)
    fun inject(fragment: EditFragment)
    fun inject(fragment: RequestToPsychologistFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): ProfileComponent
    }
}