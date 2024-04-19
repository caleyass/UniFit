package com.unifit.unifit.domain.repositories

import androidx.paging.PagingData
import com.unifit.unifit.data.remote.dto.FitnessCategoryDto
import com.unifit.unifit.domain.data.FitnessCategory
import com.unifit.unifit.domain.data.FitnessWorkout
import kotlinx.coroutines.flow.Flow

interface FitnessRepository {
    fun getFitnessProgramWorkouts(categoryName : String): Flow<PagingData<FitnessWorkout>>
    fun getFitnessCategories(): Flow<PagingData<FitnessCategory>>
}