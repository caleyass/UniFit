package com.unifit.unifit.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fitness_category")
data class FitnessCategoryEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String,
    var imageUri:String?
)