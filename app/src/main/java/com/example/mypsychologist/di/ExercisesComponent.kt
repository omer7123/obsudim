package com.example.mypsychologist.di

import com.example.mypsychologist.ui.exercises.definitionPGExerciseFragment.DefinitionProblemGroupExerciseFragment
import com.example.mypsychologist.ui.exercises.exercisesFragment.ExercisesFragment
import com.example.mypsychologist.ui.exercises.freeDiaryTrackerMoodFragment.FreeDiaryTrackerMoodFragment
import com.example.mypsychologist.ui.exercises.newCbtDiaryFragment.FragmentNewCBTDiary
import com.example.mypsychologist.ui.exercises.newFreeDiaryFragment.NewFreeDiaryFragment
import com.example.mypsychologist.ui.exercises.recordsExerciseFragment.RecordsExerciseFragment
import com.example.mypsychologist.ui.exercises.statementPATFragment.StatementProblemsAndTargetFragment
import com.example.mypsychologist.ui.exercises.trackerMoodBottomSheetFragment.TrackerMoodFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [ExercisesModule::class])
interface ExercisesComponent {
    fun inject(fragment: ExercisesFragment)
    fun inject(fragment: DefinitionProblemGroupExerciseFragment)
    fun inject(fragment: RecordsExerciseFragment)
    fun inject(fragment: FragmentNewCBTDiary)
    fun inject(fragment: NewFreeDiaryFragment)
    fun inject(fragment: TrackerMoodFragment)
    fun inject(fragment: FreeDiaryTrackerMoodFragment)
    fun inject(fragment: StatementProblemsAndTargetFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): ExercisesComponent
    }
}