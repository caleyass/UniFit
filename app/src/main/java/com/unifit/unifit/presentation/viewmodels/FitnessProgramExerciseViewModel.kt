package com.unifit.unifit.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unifit.unifit.data.utils.Resource
import com.unifit.unifit.domain.data.FitnessExercise
import com.unifit.unifit.domain.data.FitnessWorkoutPart
import com.unifit.unifit.domain.usecases.GetFitnessProgramExerciseUseCase
import com.unifit.unifit.domain.usecases.GetFitnessProgramExercisesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class FitnessProgramExerciseViewModel @Inject constructor(
    private val getFitnessProgramExerciseUseCase: GetFitnessProgramExerciseUseCase,
    private val getFitnessProgramExercisesUseCase: GetFitnessProgramExercisesUseCase
) : ViewModel() {
    var indexExercise: Int = 0
    var indexPart: Int = 0
    var counter: Int = 0

    val workoutParts = arrayListOf<String>("Warm-up", "Main", "Cool down")

    var category: String? = "Abdomen"
    var nameOfWorkout: String? = "Ab Burn Circuit"
    var nameOfWorkoutPart: String? = workoutParts[indexPart]

    var workoutTime : Long = 0

    var fitnessExercise : Flow<Resource<FitnessExercise>>? = null
    var fitnessExercises: List<FitnessExercise>? = null


    private val kCalPerMinute : Float = 5.0F

    fun getNextFitnessProgramExercise(): Flow<Resource<FitnessExercise>>? {
        if (checkParametersInitialized()) {
            fitnessExercise = getFitnessProgramExerciseUseCase.execute(
                category!!,
                nameOfWorkout!!,
                nameOfWorkoutPart!!,
                ++indexExercise
            )

            return fitnessExercise
        }
        return null
    }

    suspend fun updateFitnessProgramExercises() : List<FitnessExercise>? {
        fitnessExercises = null
        indexExercise = 0
        nameOfWorkoutPart = workoutParts[indexPart]
        fitnessExercises = viewModelScope.async { getFitnessProgramExercises()}.await()
        return fitnessExercises
    }

    suspend fun getFitnessProgramExercises() : List<FitnessExercise>?{
        if(fitnessExercises == null && checkParametersInitialized()) {
            viewModelScope.async {
                getFitnessProgramExercisesUseCase.execute(
                    category!!,
                    nameOfWorkout!!,
                    nameOfWorkoutPart!!
                ).collect { resource ->
                    when (resource) {
                        is Resource.Error -> {
                            return@collect
                        }
                        else -> {
                            Log.d("TAG", "getFitnessProgramExercises: ${resource.data?.size}")
                            resource.data?.let {
                                fitnessExercises = it
                            }
                            indexPart++
                        }
                    }
                }
            }.await()
        }
        return fitnessExercises
    }

    fun getCurrentFitnessProgramExercise(increment:Boolean = true) : FitnessExercise?{
        val fitnessExercise : FitnessExercise? = fitnessExercises?.getOrNull(indexExercise)
        Log.d("TAG", "getCurrentFitnessProgramExercise: $indexExercise ${fitnessExercise?.name}")
        Log.d("TAG", "getCurrentFitnessProgramExercise: ${fitnessExercises?.size}")
        if(increment) {
            indexExercise++
            counter++
        }
        return fitnessExercise
    }

    fun isLastExercise() : Boolean {
        if(indexExercise == fitnessExercises?.size?.minus(1)) {
            return true
        }
        return false
    }

    fun isLastPart() : Boolean {
        return indexPart == FitnessWorkoutPart.values().size - 1
    }

    suspend fun updateWorkoutPart(){
        nameOfWorkoutPart = workoutParts[indexPart]
        indexExercise = 0
        viewModelScope.launch {
            updateFitnessProgramExercises()
        }.join()
    }



    /*fun getCurrentFitnessProgramExercise(): Flow<Resource<FitnessExercise>>? {
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
    }*/

    private fun checkParametersInitialized() : Boolean {
        return category != null && nameOfWorkout != null && nameOfWorkoutPart != null
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