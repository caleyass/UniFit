package com.unifit.unifit.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.unifit.unifit.data.local.entity.FitnessCategoryEntity

@Dao
interface FitnessCategoryDao {
    @Upsert
    suspend fun upsertAll(beers: List<FitnessCategoryEntity>)
    @Query("SELECT * FROM fitness_category")
    fun pagingSource(): PagingSource<Int, FitnessCategoryEntity>
    @Query("DELETE FROM fitness_category")
    suspend fun clearAll()
}