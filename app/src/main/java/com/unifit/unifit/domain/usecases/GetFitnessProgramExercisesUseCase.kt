package com.unifit.unifit.domain.usecases

import com.unifit.unifit.data.utils.Resource
import com.unifit.unifit.domain.data.FitnessExercise
import com.unifit.unifit.domain.repositories.FitnessRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFitnessProgramExercisesUseCase @Inject constructor(private val fitnessRepository: FitnessRepository) {
    fun execute(category: String, nameOfExercise: String, nameOfWorkoutPart: String): Flow<Resource<List<FitnessExercise>>> {
        return fitnessRepository.getFitnessProgramExercises(category, nameOfExercise, nameOfWorkoutPart)
    }
}