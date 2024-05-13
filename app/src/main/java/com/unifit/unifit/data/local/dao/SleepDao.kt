package com.unifit.unifit.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.unifit.unifit.data.local.entity.SleepEntity
import kotlinx.coroutines.flow.Flow
@Dao
interface SleepDao {
    @Query("SELECT * FROM sleep")
    fun getAll(): Flow<List<SleepEntity>>
    @Query("DELETE FROM sleep WHERE id LIKE :id")
    suspend fun delete(id: Int)
}