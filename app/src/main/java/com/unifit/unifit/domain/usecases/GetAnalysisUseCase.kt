package com.unifit.unifit.domain.usecases

import com.unifit.unifit.domain.repositories.AnalysisRepository
import javax.inject.Inject

class GetAnalysisUseCase @Inject constructor(private val analysisRepository: AnalysisRepository) {
    suspend fun execute() = analysisRepository.getAllAnalysis()

}