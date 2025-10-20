package com.obsudim.mypsychologist.di

import com.obsudim.mypsychologist.ui.main.TagsFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [TagsModule::class])
interface TagsComponent {

    fun inject(fragment: TagsFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): TagsComponent
    }
}