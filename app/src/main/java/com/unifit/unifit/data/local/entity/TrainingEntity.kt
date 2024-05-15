package com.unifit.unifit.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "training")
data class TrainingEntity(
    @PrimaryKey(autoGenerate = true)
    var id : Int,
    var category : String,
    var date : LocalDate,
    var caloriesBurned : Float
)