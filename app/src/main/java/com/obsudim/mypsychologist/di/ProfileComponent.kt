package com.obsudim.mypsychologist.di

import com.obsudim.mypsychologist.ui.main.MainFragment
import com.obsudim.mypsychologist.ui.profile.editFragment.EditFragment
import com.obsudim.mypsychologist.ui.profile.feedBackFragment.FeedbackFragment
import com.obsudim.mypsychologist.ui.profile.profileFragment.ProfileFragment
import com.obsudim.mypsychologist.ui.psychologist.requestToPsychologistFragment.RequestToPsychologistFragment
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