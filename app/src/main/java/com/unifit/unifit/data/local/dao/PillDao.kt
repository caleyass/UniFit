package com.unifit.unifit.data.local.dao

import android.util.Log
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.unifit.unifit.data.local.entity.AlarmEntity
import com.unifit.unifit.data.local.entity.PillEntity
import com.unifit.unifit.data.local.entity.PillWithAlarms
import kotlinx.coroutines.flow.Flow
@Dao
interface PillDao {

    @Query("SELECT * FROM pill")
    fun getAllPills(): Flow<List<PillEntity>>
    @Query("SELECT * FROM alarm")
    fun getAllAlarms(): Flow<List<AlarmEntity>>

    @Query("DELETE FROM pill")
    suspend fun clearAllPills()

    @Query("DELETE FROM alarm")
    suspend fun clearAllAlarms()

    @Delete
    suspend fun deletePill(pill: PillEntity)

    @Query("DELETE FROM alarm WHERE objectId LIKE :pillId")
    suspend fun deleteAlarmsByPillId(pillId: Int)

    suspend fun deletePillsWithAlarms(pill: PillEntity) {
        deletePill(pill)
        deleteAlarmsByPillId(getPillById(pill.name))
    }
    @Query("SELECT id FROM pill WHERE name LIKE :pillName")
    fun getPillById(pillName: String): Int

    @Transaction
    @Query("SELECT * FROM pill WHERE id LIKE :pillId")
    fun getAllAlarmsByPillId(pillId: Int): Flow<List<PillWithAlarms>>


    @Transaction
    suspend fun insertPillWithAlarms(pill: PillEntity, alarms: List<AlarmEntity>) {
        val id = insertPill(pill)
        alarms.forEach { it.objectId = id.toInt() }
        insertAlarms(alarms)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPill(pill: PillEntity) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlarms(alarms: List<AlarmEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlarm(alarm: AlarmEntity)
}