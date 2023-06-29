package com.example.mypsychologist.di

import com.example.mypsychologist.ui.exercises.FragmentExercises
import com.example.mypsychologist.ui.exercises.cbt.FragmentDiaries
import com.example.mypsychologist.ui.exercises.cbt.FragmentDiary
import com.example.mypsychologist.ui.exercises.cbt.FragmentNewDiary
import com.example.mypsychologist.ui.exercises.rebt.FragmentREBT
import com.example.mypsychologist.ui.exercises.rebt.ProblemsFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [ExercisesModule::class])
interface ExercisesComponent {
    fun inject(fragment: FragmentExercises)
    fun inject(fragment: FragmentREBT)
    fun inject(fragment: ProblemsFragment)

    fun inject(fragment: FragmentDiaries)
    fun inject(fragment: FragmentDiary)
    fun inject(fragment: FragmentNewDiary)

    @Subcomponent.Factory
    interface Factory {
        fun create(): ExercisesComponent
    }
}