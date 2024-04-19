package com.unifit.unifit.data.mappers

import android.net.Uri
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.unifit.unifit.data.local.entity.FitnessCategoryEntity
import com.unifit.unifit.data.remote.dto.FitnessCategoryDto
import com.unifit.unifit.domain.data.FitnessCategory
import com.unifit.unifit.domain.data.FitnessWorkout

fun QueryDocumentSnapshot.toFitnessCategory() : FitnessCategory {
    return FitnessCategory(
        name = getString("name") ?: "",
        imageUri = Uri.parse(getString("image") ?: "")
    )
}

fun QueryDocumentSnapshot.toFitnessWorkout() : FitnessWorkout {
    return FitnessWorkout(
        name = getString("name") ?: "",
    )
}

fun FitnessCategoryDto.toFitnessCategory(): FitnessCategory {
    return FitnessCategory(
        name = name,
        imageUri = imageUri
    )
}