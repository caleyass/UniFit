package com.unifit.unifit.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "analysis")
data class AnalysisEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name:String,
    val date: Date
)