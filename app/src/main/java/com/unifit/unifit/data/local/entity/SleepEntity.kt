package com.unifit.unifit.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "sleep")
data class SleepEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var start : Date,
    var end : Date,
    var quality : Int
    )