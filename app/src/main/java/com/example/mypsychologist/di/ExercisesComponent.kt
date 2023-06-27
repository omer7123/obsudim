package com.example.mypsychologist.di

import com.example.mypsychologist.ui.exercises.FragmentExercises
import com.example.mypsychologist.ui.exercises.FragmentREBT
import com.example.mypsychologist.ui.exercises.cbt.FragmentCBT
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [ExercisesModule::class])
interface ExercisesComponent {
    fun inject(fragment: FragmentExercises)
    fun inject(fragment: FragmentREBT)
    fun inject(fragment: FragmentCBT)

    @Subcomponent.Factory
    interface Factory {
        fun create(): ExercisesComponent
    }
}