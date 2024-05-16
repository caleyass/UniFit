package com.unifit.unifit.domain.repositories

import com.unifit.unifit.data.local.entity.AnalysisEntity
import kotlinx.coroutines.flow.Flow

interface AnalysisRepository {
    suspend fun getAllAnalysis() : Flow<List<AnalysisEntity>>
}