package com.unifit.unifit.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.unifit.unifit.data.remote.dto.FitnessCategoryDto
import com.unifit.unifit.domain.data.FitnessCategory
import com.unifit.unifit.domain.data.FitnessWorkout
import com.unifit.unifit.domain.usecases.GetFitnessCategoriesUseCase
import com.unifit.unifit.domain.usecases.GetFitnessProgramWorkoutsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class FitnessViewModel @Inject constructor(
    private val getFitnessCategoriesUseCase: GetFitnessCategoriesUseCase,
    private val getFitnessProgramWorkoutsUseCase: GetFitnessProgramWorkoutsUseCase
): ViewModel() {
    val flowFitnessCategories = getFitnessCategories()
    lateinit var flowFitnessWorkouts : Flow<PagingData<FitnessWorkout>>
    private fun getFitnessCategories() : Flow<PagingData<FitnessCategory>> {
        return getFitnessCategoriesUseCase
            .execute()
            .cachedIn(viewModelScope)
    }

    fun getFitnessProgramWorkouts(categoryName : String) : Flow<PagingData<FitnessWorkout>>{
        return getFitnessProgramWorkoutsUseCase
            .execute(categoryName)
            .cachedIn(viewModelScope)
    }
}