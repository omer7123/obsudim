package com.example.mypsychologist.di

import com.example.mypsychologist.ui.main.TagsFragment
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