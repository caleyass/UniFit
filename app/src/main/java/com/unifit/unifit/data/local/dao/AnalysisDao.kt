package com.unifit.unifit.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.unifit.unifit.data.local.entity.AnalysisEntity
import kotlinx.coroutines.flow.Flow
@Dao
interface AnalysisDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(analysisEntity: AnalysisEntity)

    @Query("SELECT * FROM analysis")
    fun getAll(): Flow<List<AnalysisEntity>>

    @Query("SELECT * FROM analysis WHERE id LIKE :id")
    fun getAnalysis(id: Int): Flow<AnalysisEntity>

    @Delete
    suspend fun delete(analysisEntity: AnalysisEntity)

    @Query("DELETE FROM analysis")
    suspend fun clearAll()

}