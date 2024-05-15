package com.unifit.unifit.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Upsert
import com.unifit.unifit.data.local.entity.TrainingEntity

@Dao
interface TrainingDao {
    @Upsert
    suspend fun upsertExercises(exercises: TrainingEntity)

    //TODO: add here loading next exercise in advance
    /*@Query("SELECT * FROM fitness_exercises")
    fun getExercises(name:String, part:String): Flow<List<com.google.firebase.firestore.Query>>
*/
    @Delete
    suspend fun deleteExercise(exercise: TrainingEntity)
}