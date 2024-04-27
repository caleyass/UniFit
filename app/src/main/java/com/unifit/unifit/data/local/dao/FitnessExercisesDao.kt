package com.unifit.unifit.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.unifit.unifit.data.local.entity.FitnessExercisesEntity
import com.unifit.unifit.domain.data.FitnessExercise
import kotlinx.coroutines.flow.Flow

@Dao
interface FitnessExercisesDao {
    @Upsert
    suspend fun upsertExercises(exercises: FitnessExercisesEntity)

    //TODO: add here loading next exercise in advance
    /*@Query("SELECT * FROM fitness_exercises")
    fun getExercises(name:String, part:String): Flow<List<com.google.firebase.firestore.Query>>
*/
    @Delete
    suspend fun deleteExercise(exercise: FitnessExercisesEntity)
}