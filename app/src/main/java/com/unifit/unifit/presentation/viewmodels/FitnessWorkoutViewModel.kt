package com.unifit.unifit.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.unifit.unifit.domain.data.FitnessWorkout
import com.unifit.unifit.domain.usecases.GetFitnessProgramWorkoutsUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow

class FitnessWorkoutViewModel @AssistedInject constructor(
    @Assisted private val categoryName: String,
    private val getFitnessProgramWorkoutsUseCase: GetFitnessProgramWorkoutsUseCase
) : ViewModel(){
    override fun onCleared() {
        Log.d("TAG", "onCleared: FitnessWorkoutViewModel")
        super.onCleared()
    }
    val flowFitnessWorkouts = getFitnessProgramWorkouts()

    fun getFitnessProgramWorkouts() : Flow<PagingData<FitnessWorkout>> {
        Log.d("TAG", "getFitnessProgramWorkouts: $categoryName")
        return getFitnessProgramWorkoutsUseCase
            .execute(categoryName)
            .cachedIn(viewModelScope)
    }
    @AssistedFactory
    interface FitnessWorkoutFactory {
        fun create(categoryName: String): FitnessWorkoutViewModel
    }
    companion object{
        fun provideFitnessWorkoutViewModelFactory(
            fitnessWorkoutFactory : FitnessWorkoutFactory,
            categoryName: String
        ) : ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return fitnessWorkoutFactory.create(categoryName) as T
                }
            }
        }
    }
}