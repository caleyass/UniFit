package com.unifit.unifit.data.local.entity

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fitness_exercise")
class FitnessExerciseEntity {
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
    var gif : Uri = Uri.EMPTY
    var name: String = ""
    var time: Long = 0L
}