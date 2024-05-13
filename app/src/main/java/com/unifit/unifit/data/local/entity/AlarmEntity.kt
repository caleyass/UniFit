package com.unifit.unifit.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alarm")
data class AlarmEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var objectId : Int? = null,
    var hour: Int,
    var minute: Int,
    var isOn: Boolean,
    var label: String,
    var text:String,
)