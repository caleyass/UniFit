package com.unifit.unifit.domain.usecases

import com.unifit.unifit.data.remote.dto.FitnessCategory
import com.unifit.unifit.domain.repositories.FitnessRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFitnessCategoriesUseCase @Inject constructor(private val fitnessRepository: FitnessRepository) {
    fun execute(): Flow<List<FitnessCategory>> {
        return fitnessRepository
            .getFitnessPrograms()
    }
}