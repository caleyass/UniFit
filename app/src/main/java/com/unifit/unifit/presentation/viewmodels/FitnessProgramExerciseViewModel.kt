package com.unifit.unifit.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.unifit.unifit.data.utils.Resource
import com.unifit.unifit.domain.data.FitnessExercise
import com.unifit.unifit.domain.usecases.GetFitnessProgramExerciseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
@HiltViewModel
class FitnessProgramExerciseViewModel @Inject constructor(
    private val getFitnessProgramExerciseUseCase: GetFitnessProgramExerciseUseCase
) : ViewModel() {
    var category: String? = "Abdomen"
    var nameOfWorkout: String? = "Ab Burn Circuit"
    var nameOfWorkoutPart: String? = null

    var index: Int = 0

    var workoutTime : Long = 0

    var fitnessExercise : Flow<Resource<FitnessExercise>>? = null

    private val kCalPerMinute : Float = 5.0F

    fun getNextFitnessProgramExercise(): Flow<Resource<FitnessExercise>>? {
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



    fun getCurrentFitnessProgramExercise(): Flow<Resource<FitnessExercise>>? {
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

    private fun getKCal(weight:Int) : Float {
        return (kCalPerMinute * weight * workoutTime)/60
    }
    fun getKCalFormatted(weight:Int) : String {
        return String.format("%.2f", getKCal(weight))
    }

    fun getTimeFormatted() : String {
        val minutes = workoutTime / 60
        val seconds = workoutTime % 60
        return String.format("%02d:%02d", minutes, seconds)
    }
}