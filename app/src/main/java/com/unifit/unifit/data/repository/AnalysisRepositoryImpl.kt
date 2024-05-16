package com.unifit.unifit.data.repository

import com.unifit.unifit.data.local.dao.AnalysisDao
import com.unifit.unifit.domain.repositories.AnalysisRepository
import javax.inject.Inject

class AnalysisRepositoryImpl @Inject constructor(
    private val analysisDao: AnalysisDao
) : AnalysisRepository {
    override suspend fun getAllAnalysis() = analysisDao.getAll()
}