package com.example.mypsychologist.di

import com.example.mypsychologist.ui.exercises.FragmentExercises
import com.example.mypsychologist.ui.exercises.cbt.FragmentNewCBTDiary
import com.example.mypsychologist.ui.exercises.cbt.FragmentThoughtDiary
import com.example.mypsychologist.ui.exercises.cbt.FreeDiaryFragment
import com.example.mypsychologist.ui.exercises.cbt.NewFreeDiaryFragment
import com.example.mypsychologist.ui.exercises.cbt.TrackerMoodFragment
import com.example.mypsychologist.ui.exercises.cbt.exerciseResultsFragment.FragmentDiaries
import com.example.mypsychologist.ui.exercises.rebt.AutoDialogFragment
import com.example.mypsychologist.ui.exercises.rebt.NewProblemFragment
import com.example.mypsychologist.ui.exercises.rebt.ProblemsFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [ExercisesModule::class])
interface ExercisesComponent {
    fun inject(fragment: FragmentExercises)

    fun inject(fragment: ProblemsFragment)
    fun inject(fragment: NewProblemFragment)
    fun inject(autoDialogFragment: AutoDialogFragment)


    fun inject(fragment: FragmentDiaries)
    fun inject(fragment: FragmentThoughtDiary)
    fun inject(fragment: FragmentNewCBTDiary)
    fun inject(fragment: FreeDiaryFragment)
    fun inject(fragment: NewFreeDiaryFragment)
    fun inject(fragment: TrackerMoodFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): ExercisesComponent
    }
}