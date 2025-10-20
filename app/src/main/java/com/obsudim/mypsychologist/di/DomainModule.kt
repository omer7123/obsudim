package com.obsudim.mypsychologist.di

import androidx.lifecycle.ViewModel
import com.obsudim.mypsychologist.data.repository.AuthenticationRepositoryImpl
import com.obsudim.mypsychologist.data.repository.EducationRepositoryImpl
import com.obsudim.mypsychologist.data.repository.ExerciseReposityoryImpl
import com.obsudim.mypsychologist.data.repository.FreeDiaryRepositoryImpl
import com.obsudim.mypsychologist.data.repository.TestsDiagnosticRepositoryImpl
import com.obsudim.mypsychologist.domain.repository.retrofit.AuthenticationRepository
import com.obsudim.mypsychologist.domain.repository.retrofit.EducationRepository
import com.obsudim.mypsychologist.domain.repository.retrofit.ExerciseRepository
import com.obsudim.mypsychologist.domain.repository.retrofit.FreeDiaryRepository
import com.obsudim.mypsychologist.domain.repository.retrofit.TestsDiagnosticRepository
import com.obsudim.mypsychologist.presentation.authentication.authFragment.AuthViewModel
import com.obsudim.mypsychologist.presentation.authentication.registrationFragment.RegisterViewModel
import com.obsudim.mypsychologist.presentation.diagnostics.passingTestFragment.PassingTestViewModel
import com.obsudim.mypsychologist.presentation.exercises.definitionProblemGroupExerciseFragment.DefinitionProblemGroupExerciseViewModel
import com.obsudim.mypsychologist.presentation.exercises.freeDiaryWithTrackerMoodFragment.TrackerMoodViewModel
import com.obsudim.mypsychologist.presentation.exercises.newFreeDiaryFragment.NewFreeDiaryViewModel
import com.obsudim.mypsychologist.presentation.exercises.statementProblemsAndTargetFragment.StatementProblemsAndTargetViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
interface DomainModule {

    @Binds
    @Singleton
    fun bindFreeDiaryRepository(impl: FreeDiaryRepositoryImpl): FreeDiaryRepository

    @Binds
    @Singleton
    fun bindAuthenticationRepository(impl: AuthenticationRepositoryImpl): AuthenticationRepository

    @Binds
    @Singleton
    fun bindTestsDiagnosticRepository(impl: TestsDiagnosticRepositoryImpl): TestsDiagnosticRepository

    @Binds
    @Singleton
    fun bindEducationRepository(impl: EducationRepositoryImpl): EducationRepository

    @Binds
    @Singleton
    fun bindExercise(impl: ExerciseReposityoryImpl): ExerciseRepository



    @Binds
    @[IntoMap ClassKey(RegisterViewModel::class)]
    fun provideRegisterViewModel(registerViewModel: RegisterViewModel): ViewModel
    @Binds
    @[IntoMap ClassKey(AuthViewModel::class)]
    fun provideAuthViewModel(authViewModel: AuthViewModel): ViewModel

    @Binds
    @[IntoMap ClassKey(NewFreeDiaryViewModel::class)]
    fun provideNewFreeDiaryViewModel(newFreeDiaryViewModel: NewFreeDiaryViewModel): ViewModel

    @Binds
    @[IntoMap ClassKey(PassingTestViewModel::class)]
    fun providePassingTestViewModel(passingTestViewModel: PassingTestViewModel): ViewModel

    @Binds
    @[IntoMap ClassKey(TrackerMoodViewModel::class)]
    fun provideTrackerMoodViewModel(trackerMoodViewModel: TrackerMoodViewModel): ViewModel

    @Binds
    @[IntoMap ClassKey(StatementProblemsAndTargetViewModel::class)]
    fun provideStatementProblemsAndTargetViewModel(statementProblemsAndTargetViewModel: StatementProblemsAndTargetViewModel): ViewModel

    @Binds
    @[IntoMap ClassKey(DefinitionProblemGroupExerciseViewModel::class)]
    fun provideDefinitionProlemGroupExerciseViewModel(definitionProblemGroupExerciseViewModel: DefinitionProblemGroupExerciseViewModel): ViewModel

}