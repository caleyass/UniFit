package com.unifit.unifit.domain.usecases

import androidx.paging.PagingData
import com.unifit.unifit.data.remote.dto.FitnessCategoryDto
import com.unifit.unifit.domain.data.FitnessWorkout
import com.unifit.unifit.domain.repositories.FitnessRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFitnessProgramWorkoutsUseCase @Inject constructor(private val fitnessRepository: FitnessRepository) {
    fun execute(categoryName : String): Flow<PagingData<FitnessWorkout>> {
        return fitnessRepository
            .getFitnessProgramWorkouts(categoryName)
    }
}