package com.unifit.unifit.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class PillWithAlarms (
    @Embedded val pill: PillEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "objectId"
    )
    val alarms: List<AlarmEntity>
)