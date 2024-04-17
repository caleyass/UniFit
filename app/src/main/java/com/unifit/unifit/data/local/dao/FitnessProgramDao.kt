package com.unifit.unifit.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.unifit.unifit.data.local.entity.FitnessProgram

@Dao
interface FitnessProgramDao {
    @Query("SELECT * FROM fitness_program WHERE id = :id")
    fun getFitnessProgram(id:Int) : LiveData<FitnessProgram>
}