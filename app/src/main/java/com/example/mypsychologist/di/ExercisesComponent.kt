package com.example.mypsychologist.di

import com.example.mypsychologist.presentation.exercises.freeDiaryWithTrackerMoodFragment.FreeDiaryTrackerMoodFragment
import com.example.mypsychologist.ui.exercises.DefinitionProblemGroupExerciseFragment
import com.example.mypsychologist.ui.exercises.FragmentExercises
import com.example.mypsychologist.ui.exercises.cbt.FragmentNewCBTDiary
import com.example.mypsychologist.ui.exercises.cbt.FreeDiaryFragment
import com.example.mypsychologist.ui.exercises.cbt.NewFreeDiaryFragment
import com.example.mypsychologist.ui.exercises.cbt.TrackerMoodFragment
import com.example.mypsychologist.ui.exercises.cbt.exerciseResultsFragment.FragmentDiaries
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [ExercisesModule::class])
interface ExercisesComponent {
    fun inject(fragment: FragmentExercises)
    fun inject(fragment: DefinitionProblemGroupExerciseFragment)
    fun inject(fragment: FragmentDiaries)
    fun inject(fragment: FragmentNewCBTDiary)
    fun inject(fragment: FreeDiaryFragment)
    fun inject(fragment: NewFreeDiaryFragment)
    fun inject(fragment: TrackerMoodFragment)
    fun inject(fragment: FreeDiaryTrackerMoodFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): ExercisesComponent
    }
}