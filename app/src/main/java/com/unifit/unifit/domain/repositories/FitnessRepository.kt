package com.unifit.unifit.domain.repositories

import com.unifit.unifit.data.remote.dto.FitnessCategory
import kotlinx.coroutines.flow.Flow

interface FitnessRepository {
    fun getFitnessPrograms(): Flow<List<FitnessCategory>>

    fun getFitnessProgramWorkouts(categoryName : String): Flow<List<FitnessCategory>>

}