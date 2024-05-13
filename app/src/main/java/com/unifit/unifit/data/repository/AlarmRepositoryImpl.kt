package com.unifit.unifit.data.repository

import com.unifit.unifit.data.local.dao.AlarmDao
import com.unifit.unifit.data.local.entity.AlarmEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AlarmRepositoryImpl @Inject constructor(
    private val alarmDao: AlarmDao
)  {
    fun getAlarm(id:Int): Flow<AlarmEntity> {
        return alarmDao.getAlarm(id)
    }

    suspend fun insertAlarm(alarm: AlarmEntity) {
        alarmDao.insert(alarm)
    }

    suspend fun deleteAlarm(alarm: AlarmEntity) {
        alarmDao.delete(alarm)
    }

    suspend fun updateAlarm(alarm: AlarmEntity) {
        alarmDao.update(alarm)
    }
}