package com.unifit.unifit.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.unifit.unifit.data.local.entity.FitnessCategoryEntity

@Dao
interface FitnessCategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(fitnessCategories: List<FitnessCategoryEntity>)
    @Query("SELECT * FROM fitness_category WHERE name LIKE :query")
    fun pagingSource(query: String): PagingSource<Int, FitnessCategoryEntity>
    @Query("DELETE FROM fitness_category")
    suspend fun clearAll()
}