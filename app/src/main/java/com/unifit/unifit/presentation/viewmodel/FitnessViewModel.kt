package com.unifit.unifit.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unifit.unifit.data.remote.dto.FitnessCategory
import com.unifit.unifit.domain.repositories.FitnessRepository
import com.unifit.unifit.domain.usecases.GetFitnessCategoriesUseCase
import com.unifit.unifit.domain.usecases.GetFitnessProgramWorkoutsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class FitnessViewModel @Inject constructor(
    private val repository: FitnessRepository
) : ViewModel() {
    @Inject lateinit var getFitnessCategoriesUseCase: GetFitnessCategoriesUseCase
    @Inject lateinit var getFitnessProgramWorkoutsUseCase: GetFitnessProgramWorkoutsUseCase

    fun getFitnessCategories() : SharedFlow<List<FitnessCategory>>{
        return getFitnessCategoriesUseCase
            .execute()
            .stateIn(
                scope = viewModelScope,
                initialValue = emptyList(),
                started = SharingStarted.WhileSubscribed(5000),
            )
    }

    fun getFitnessProgramWorkouts(categoryName : String) : SharedFlow<List<FitnessCategory>>{
        return getFitnessProgramWorkoutsUseCase
            .execute(categoryName)
            .stateIn(
                scope = viewModelScope,
                initialValue = emptyList(),
                started = SharingStarted.WhileSubscribed(5000),
            )
    }
}