package com.unifit.unifit.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.unifit.unifit.data.local.dao.FitnessProgramDao
import com.unifit.unifit.data.local.entity.FitnessProgram

@Database(entities = [FitnessProgram::class], version = 1, exportSchema = false)
abstract class FitnessDatabase : RoomDatabase() {
    abstract fun fitnessProgramDao() : FitnessProgramDao

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