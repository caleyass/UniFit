package com.unifit.unifit.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.unifit.unifit.domain.data.FitnessExercise
import com.unifit.unifit.domain.usecases.GetFitnessProgramExerciseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
@HiltViewModel
class FitnessProgramExerciseViewModel @Inject constructor(
    private val getFitnessProgramExerciseUseCase: GetFitnessProgramExerciseUseCase
) : ViewModel() {
    var category: String? = null
    var nameOfWorkout: String? = null
    var nameOfWorkoutPart: String? = null

    var index: Int = 0

    var fitnessExercise : Flow<FitnessExercise?>? = null
    fun getNextFitnessProgramExercise(): Flow<FitnessExercise?>? {
        if (category != null && nameOfWorkout != null && nameOfWorkoutPart != null) {
            fitnessExercise = getFitnessProgramExerciseUseCase.execute(
                category!!,
                nameOfWorkout!!,
                nameOfWorkoutPart!!,
                ++index
            )
            return fitnessExercise
        }
        return null
    }

    fun getCurrentFitnessProgramExercise(): Flow<FitnessExercise?>? {
        if (category != null && nameOfWorkout != null && nameOfWorkoutPart != null) {
            if(fitnessExercise == null) {
                fitnessExercise = getFitnessProgramExerciseUseCase.execute(
                    category!!,
                    nameOfWorkout!!,
                    nameOfWorkoutPart!!,
                    index
                )
            }
            return fitnessExercise
        }
        return null
    }

}