package com.unifit.unifit.domain.repositories

import androidx.paging.PagingData
import com.unifit.unifit.data.remote.dto.FitnessCategoryDto
import com.unifit.unifit.data.utils.Resource
import com.unifit.unifit.domain.data.FitnessCategory
import com.unifit.unifit.domain.data.FitnessExercise
import com.unifit.unifit.domain.data.FitnessWorkout
import kotlinx.coroutines.flow.Flow

interface FitnessRepository {
    fun getFitnessProgramWorkouts(categoryName: String): Flow<PagingData<FitnessWorkout>>
    fun getFitnessCategories(): Flow<PagingData<FitnessCategory>>
    fun getFitnessProgramExercise(
        category: String,
        nameOfExercise: String,
        nameOfWorkoutPart: String,
        index: Int
    ): Flow<Resource<FitnessExercise>>

    fun getFitnessProgramExercises(
        category: String,
        nameOfWorkout: String,
        nameOfWorkoutPart: String
    ): Flow<Resource<List<FitnessExercise>>>
}