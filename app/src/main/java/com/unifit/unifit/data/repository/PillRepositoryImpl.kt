package com.unifit.unifit.data.repository

import com.unifit.unifit.data.local.dao.PillDao
import com.unifit.unifit.data.local.entity.AlarmEntity
import com.unifit.unifit.data.local.entity.PillEntity
import com.unifit.unifit.domain.repositories.PillRepository
import java.util.concurrent.Flow
import javax.inject.Inject

class PillRepositoryImpl @Inject constructor(
    private val pillDao: PillDao
)  : PillRepository {
    suspend fun insertPill(pill: PillEntity) {
        pillDao.insertPill(pill)
    }

    suspend fun insertPill(pill: PillEntity, alarms: List<AlarmEntity>) {
        pillDao.insertPillWithAlarms(pill, alarms)
    }

    override suspend fun getAllPills() = pillDao.getAllPills()


}