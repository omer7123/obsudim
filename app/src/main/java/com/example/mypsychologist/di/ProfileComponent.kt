package com.example.mypsychologist.di

import com.example.mypsychologist.ui.main.FeedbackFragment
import com.example.mypsychologist.ui.main.ProfileFragment
import com.example.mypsychologist.ui.main.PsychologistQuestionnaireFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [ProfileModule::class])
interface ProfileComponent {
    fun inject(fragment: ProfileFragment)
    fun inject(fragment: FeedbackFragment)
    fun inject(fragment: PsychologistQuestionnaireFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): ProfileComponent
    }
}