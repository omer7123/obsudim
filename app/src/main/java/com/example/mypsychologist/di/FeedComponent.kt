package com.example.mypsychologist.di

import com.example.mypsychologist.ui.feed.FeedFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [FeedModule::class])
interface FeedComponent {
    fun inject(fragment: FeedFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): FeedComponent
    }
}