package com.obsudim.mypsychologist.di

import com.obsudim.mypsychologist.ui.profile.profileFragment.NotificationFragment
import dagger.Subcomponent

@Subcomponent
interface NotificationComponent {
    fun inject(fragment: NotificationFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): NotificationComponent
    }
}