package com.unifit.unifit.domain.data

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable

data class FitnessProgramExercises (
    val warmUp:List<FitnessExercise>,
    val mainWorkout:List<FitnessExercise>,
    val coolDown:List<FitnessExercise>
)

data class FitnessExercise(
    val gif : Uri,
    val name: String,
    val time: Long,
)

