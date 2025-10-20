package com.obsudim.mypsychologist.di

import com.obsudim.mypsychologist.ui.authentication.authFragment.AuthFragment
import com.obsudim.mypsychologist.ui.authentication.registrationFragment.RegistrationFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent
interface AuthenticationComponent {
    fun inject(fragment: RegistrationFragment)
    fun inject(fragment: AuthFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): AuthenticationComponent
    }
}