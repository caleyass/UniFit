package com.unifit.unifit.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.unifit.unifit.data.local.dao.FitnessCategoryDao
import com.unifit.unifit.data.local.dao.FitnessExercisesDao
import com.unifit.unifit.data.local.entity.FitnessCategoryEntity
import com.unifit.unifit.data.local.entity.FitnessExercisesEntity

@Database(entities = [FitnessCategoryEntity::class, FitnessExercisesEntity::class], version = 1, exportSchema = false)
abstract class FitnessDatabase : RoomDatabase() {
    abstract val fitnessExercisesDao : FitnessExercisesDao

    companion object{
        @Volatile
        private var INSTANCE: FitnessDatabase? = null

        fun getDatabase(context: Context): FitnessDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FitnessDatabase::class.java,
                    "fitness_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}