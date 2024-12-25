package com.example.mypsychologist.di

import androidx.lifecycle.ViewModel
import com.example.mypsychologist.data.repository.AuthenticationRepositoryImpl
import com.example.mypsychologist.data.repository.EducationRepositoryImpl
import com.example.mypsychologist.data.repository.ExerciseReposityoryImpl
import com.example.mypsychologist.data.repository.FreeDiaryRepositoryImpl
import com.example.mypsychologist.data.repository.TestsDiagnosticRepositoryImpl
import com.example.mypsychologist.domain.repository.retrofit.AuthenticationRepository
import com.example.mypsychologist.domain.repository.retrofit.EducationRepository
import com.example.mypsychologist.domain.repository.retrofit.ExerciseRepository
import com.example.mypsychologist.domain.repository.retrofit.FreeDiaryRepository
import com.example.mypsychologist.domain.repository.retrofit.TestsDiagnosticRepository
import com.example.mypsychologist.presentation.authentication.authFragment.AuthViewModel
import com.example.mypsychologist.presentation.authentication.registrationFragment.RegisterViewModel
import com.example.mypsychologist.presentation.diagnostics.PassingTestViewModel
import com.example.mypsychologist.presentation.exercises.FreeDiariesViewModel
import com.example.mypsychologist.presentation.exercises.NewFreeDiaryViewModel
import com.example.mypsychologist.presentation.exercises.trackerMoodFragment.TrackerMoodViewModel
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
    @[IntoMap ClassKey(FreeDiariesViewModel::class)]
    fun provideFreeDiariesViewModel(freeDiariesViewModel: FreeDiariesViewModel): ViewModel

    @Binds
    @[IntoMap ClassKey(NewFreeDiaryViewModel::class)]
    fun provideNewFreeDiaryViewModel(newFreeDiaryViewModel: NewFreeDiaryViewModel): ViewModel

    @Binds
    @[IntoMap ClassKey(PassingTestViewModel::class)]
    fun providePassingTestViewModel(passingTestViewModel: PassingTestViewModel): ViewModel

    @Binds
    @[IntoMap ClassKey(TrackerMoodViewModel::class)]
    fun provideTrackerMoodViewModel(trackerMoodViewModel: TrackerMoodViewModel): ViewModel

}