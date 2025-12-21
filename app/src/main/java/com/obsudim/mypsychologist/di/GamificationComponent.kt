package com.obsudim.mypsychologist.di

import com.obsudim.mypsychologist.ui.gamification.GamificationFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [GamificationModule::class])
interface GamificationComponent {

    fun inject(fragment: GamificationFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): GamificationComponent
    }
}