package com.example.mypsychologist.di

import com.example.mypsychologist.ui.main.*
import com.example.mypsychologist.ui.psychologist.RequestToPsychologistFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [ProfileModule::class])
interface ProfileComponent {

    fun inject(fragment: MainFragment)
    fun inject(fragment: ProfileFragment)
    fun inject(fragment: FeedbackFragment)
    fun inject(fragment: PsychologistQuestionnaireFragment)
    fun inject(fragment: EditFragment)
    fun inject(fragment: ClientInfoFragment)
    fun inject(clientsFragment: ClientsFragment)
    fun inject(tasksFragment: ClientTasksFragment)
    fun inject(fragment: RequestToPsychologistFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): ProfileComponent
    }
}