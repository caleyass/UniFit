package com.unifit.unifit.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "analysis")
data class AnalysisEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name:String,
    var date: LocalDate,
    var doctor:String? = null
)