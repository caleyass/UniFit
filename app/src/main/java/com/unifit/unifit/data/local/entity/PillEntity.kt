package com.unifit.unifit.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.util.Date

@Entity(tableName = "pill")
data class PillEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String,
    var dose:Int,
    var endDate: LocalDate
)