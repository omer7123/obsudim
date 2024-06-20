package com.example.mypsychologist.di

import android.content.Context
import com.example.mypsychologist.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Scope
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class, DomainModule::class, DataSourceModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)

    fun apiUrlProvider(): ApiUrlProvider

    fun exercisesComponent(): ExercisesComponent.Factory
    fun diagnosticComponent(): DiagnosticComponent.Factory
    fun profileComponent(): ProfileComponent.Factory
    fun psychologistComponent(): PsychologistComponent.Factory
    fun feedComponent(): FeedComponent.Factory
    fun educationComponent(): EducationComponent.Factory
    fun authenticationComponent(): AuthenticationComponent.Factory
    fun tagsComponent(): TagsComponent.Factory

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}

@Scope
@Retention(value = AnnotationRetention.RUNTIME)
annotation class FragmentScope