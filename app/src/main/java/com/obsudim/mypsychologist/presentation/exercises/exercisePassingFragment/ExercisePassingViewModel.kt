package com.obsudim.mypsychologist.presentation.exercises.exercisePassingFragment

import androidx.lifecycle.ViewModel
import com.obsudim.mypsychologist.domain.useCase.exerciseUseCases.GetExerciseDetailUseCase
import javax.inject.Inject

class ExercisePassingViewModel @Inject constructor(
    private val getExerciseDetailUseCase: GetExerciseDetailUseCase
): ViewModel() {


}