package com.obsudim.mypsychologist.di

import android.content.Context
import com.obsudim.mypsychologist.App
import com.obsudim.mypsychologist.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Scope
import javax.inject.Singleton

@Singleton
@Component(modules = [WorkerModule::class, DataModule::class, DomainModule::class, DataSourceModule::class, ProfileModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(app: App)

    fun apiUrlProvider(): ApiUrlProvider

    fun exercisesComponent(): ExercisesComponent.Factory
    fun diagnosticComponent(): DiagnosticComponent.Factory
    fun profileComponent(): ProfileComponent.Factory
    fun psychologistComponent(): PsychologistComponent.Factory

    fun educationComponent(): EducationComponent.Factory
    fun authenticationComponent(): AuthenticationComponent.Factory
    fun gamificationComponent(): GamificationComponent.Factory


    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}

@Scope
@Retention(value = AnnotationRetention.RUNTIME)
annotation class FragmentScope