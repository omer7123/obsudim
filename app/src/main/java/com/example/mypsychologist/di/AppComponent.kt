package com.example.mypsychologist.di

import android.content.Context
import com.example.mypsychologist.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Scope
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)

    fun exercisesComponent(): ExercisesComponent.Factory
    fun diagnosticComponent(): DiagnosticComponent.Factory
    fun profileComponent(): ProfileComponent.Factory
    fun psychologistComponent(): PsychologistComponent.Factory

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}

@Scope
@Retention(value = AnnotationRetention.RUNTIME)
annotation class FragmentScope