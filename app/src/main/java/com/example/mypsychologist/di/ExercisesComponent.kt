package com.example.mypsychologist.di

import com.example.mypsychologist.ui.exercises.FragmentExercises
import com.example.mypsychologist.ui.exercises.cbt.FragmentNewCBTDiary
import com.example.mypsychologist.ui.exercises.cbt.FragmentThoughtDiary
import com.example.mypsychologist.ui.exercises.cbt.FreeDiaryFragment
import com.example.mypsychologist.ui.exercises.cbt.NewFreeDiaryFragment
import com.example.mypsychologist.ui.exercises.cbt.TrackerMoodFragment
import com.example.mypsychologist.ui.exercises.cbt.exerciseResultsFragment.FragmentDiaries
import com.example.mypsychologist.ui.exercises.rebt.AutoDialogFragment
import com.example.mypsychologist.ui.exercises.rebt.BeliefAnalysisFragment
import com.example.mypsychologist.ui.exercises.rebt.BeliefVerificationFragment
import com.example.mypsychologist.ui.exercises.rebt.BeliefsAnalysisFragment
import com.example.mypsychologist.ui.exercises.rebt.BeliefsVerificationFragment
import com.example.mypsychologist.ui.exercises.rebt.NewProblemFragment
import com.example.mypsychologist.ui.exercises.rebt.ProblemsFragment
import com.example.mypsychologist.ui.exercises.rebt.RebtAlternativeThoughtFragment
import com.example.mypsychologist.ui.exercises.rebt.RebtHarmfulThoughtFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [ExercisesModule::class])
interface ExercisesComponent {
    fun inject(fragment: FragmentExercises)

    fun inject(fragment: ProblemsFragment)
    fun inject(fragment: NewProblemFragment)
    fun inject(rebtHarmfulThoughtFragment: RebtHarmfulThoughtFragment)
    fun inject(rebtAlternativeThoughtFragment: RebtAlternativeThoughtFragment)
    fun inject(beliefVerificationFragment: BeliefVerificationFragment)
    fun inject(beliefsVerificationFragment: BeliefsVerificationFragment)
    fun inject(beliefsAnalysisFragment: BeliefsAnalysisFragment)
    fun inject(beliefAnalysisFragment: BeliefAnalysisFragment)
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