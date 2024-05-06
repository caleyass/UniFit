package com.unifit.unifit.domain.usecases

import com.unifit.unifit.domain.data.FitnessExercise
import com.unifit.unifit.domain.repositories.FitnessRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFitnessProgramExerciseUseCase @Inject constructor(private val fitnessRepository: FitnessRepository) {
    fun execute(category: String, nameOfExercise: String, nameOfWorkoutPart: String, index: Int): Flow<FitnessExercise?> {
        return fitnessRepository.getFitnessProgramExercise(category, nameOfExercise, nameOfWorkoutPart, index)
    }
}