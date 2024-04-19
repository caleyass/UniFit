package com.unifit.unifit.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.log
import com.unifit.unifit.domain.data.FitnessCategory
import com.unifit.unifit.domain.data.FitnessWorkout
import com.unifit.unifit.domain.usecases.GetFitnessCategoriesUseCase
import com.unifit.unifit.domain.usecases.GetFitnessProgramWorkoutsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class FitnessCategoryViewModel @Inject constructor(
    private val getFitnessCategoriesUseCase: GetFitnessCategoriesUseCase,
): ViewModel() {
    override fun onCleared() {
        Log.d("TAG", "onCleared: FitnessCategoryViewModel")
        super.onCleared()
    }
    val flowFitnessCategories = getFitnessCategories()
    private fun getFitnessCategories() : Flow<PagingData<FitnessCategory>> {
        Log.d("TAG", "getFitnessCategories: ")
        return getFitnessCategoriesUseCase
            .execute()
            .cachedIn(viewModelScope)
    }


}