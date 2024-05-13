package com.unifit.unifit.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.unifit.unifit.data.local.converter.DateConverter
import com.unifit.unifit.data.local.converter.LocalDateConverter
import com.unifit.unifit.data.local.dao.AlarmDao
import com.unifit.unifit.data.local.dao.AnalysisDao
import com.unifit.unifit.data.local.dao.FitnessExercisesDao
import com.unifit.unifit.data.local.dao.PillDao
import com.unifit.unifit.data.local.dao.SleepDao
import com.unifit.unifit.data.local.entity.AlarmEntity
import com.unifit.unifit.data.local.entity.AnalysisEntity
import com.unifit.unifit.data.local.entity.PillEntity
import com.unifit.unifit.data.local.entity.SleepEntity


@Database(entities = [
    AlarmEntity::class,
    AnalysisEntity::class,
    PillEntity::class,
    SleepEntity::class ], version = 4, exportSchema = false)
@TypeConverters(LocalDateConverter::class)
abstract class MyDatabase : RoomDatabase() {
    abstract val alarmDao : AlarmDao
    abstract val analysisDao : AnalysisDao
    abstract val pillDao : PillDao
    abstract val sleepDao : SleepDao

    companion object{
        @Volatile
        private var INSTANCE: MyDatabase? = null

        fun getDatabase(context: Context): MyDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyDatabase::class.java,
                    "my_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}