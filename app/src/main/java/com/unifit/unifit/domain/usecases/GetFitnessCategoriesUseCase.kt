package com.unifit.unifit.domain.usecases

import androidx.paging.PagingData
import com.unifit.unifit.data.remote.dto.FitnessCategoryDto
import com.unifit.unifit.domain.data.FitnessCategory
import com.unifit.unifit.domain.repositories.FitnessRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFitnessCategoriesUseCase @Inject constructor(private val fitnessRepository: FitnessRepository) {
    fun execute(): Flow<PagingData<FitnessCategory>> {
        return fitnessRepository
            .getFitnessCategories()
    }
}