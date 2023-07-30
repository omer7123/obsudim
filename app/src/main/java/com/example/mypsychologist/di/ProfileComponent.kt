package com.example.mypsychologist.di

import com.example.mypsychologist.ui.main.FeedbackFragment
import com.example.mypsychologist.ui.main.ProfileFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [ProfileModule::class])
interface ProfileComponent {
    fun inject(fragment: ProfileFragment)
    fun inject(fragment: FeedbackFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): ProfileComponent
    }
}