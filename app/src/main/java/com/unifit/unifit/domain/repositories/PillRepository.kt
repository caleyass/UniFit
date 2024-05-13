package com.unifit.unifit.domain.repositories

import com.unifit.unifit.data.local.entity.PillEntity
import kotlinx.coroutines.flow.Flow

interface PillRepository {
    suspend fun getAllPills() : Flow<List<PillEntity>>

}