package com.obsudim.mypsychologist.di

import com.obsudim.mypsychologist.ui.exercises.exercisesFragment.ExercisesFragment
import com.obsudim.mypsychologist.ui.exercises.exercisesHostFragment.ExercisesHostFragment
import com.obsudim.mypsychologist.ui.exercises.freeDiaryTrackerMoodFragment.FreeDiaryTrackerMoodFragment
import com.obsudim.mypsychologist.ui.exercises.newCbtDiaryFragment.FragmentNewCBTDiary
import com.obsudim.mypsychologist.ui.exercises.newFreeDiaryFragment.NewFreeDiaryFragment
import com.obsudim.mypsychologist.ui.exercises.trackerMoodBottomSheetFragment.TrackerMoodFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [ExercisesModule::class])
interface ExercisesComponent {
    fun inject(fragment: ExercisesFragment)
    fun inject(fragment: ExercisesHostFragment)
    fun inject(fragment: FragmentNewCBTDiary)
    fun inject(fragment: NewFreeDiaryFragment)
    fun inject(fragment: TrackerMoodFragment)
    fun inject(fragment: FreeDiaryTrackerMoodFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): ExercisesComponent
    }
}