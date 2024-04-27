package com.unifit.unifit.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unifit.unifit.domain.data.FitnessExercise
import com.unifit.unifit.domain.data.FitnessProgramExercises
import com.unifit.unifit.domain.usecases.GetFitnessProgramExerciseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class FitnessProgramExerciseViewModel @Inject constructor(
    private val getFitnessProgramExerciseUseCase: GetFitnessProgramExerciseUseCase
) : ViewModel(){
    //var fitnessExercise: Flow<FitnessExercise?>?
    suspend fun getFitnessProgramExercise(category: String, nameOfWorkoutPart: String, index: Int) : Flow<FitnessExercise?> {
        return getFitnessProgramExerciseUseCase.execute(category, nameOfWorkoutPart, index)

    }
}