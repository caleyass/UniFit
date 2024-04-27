package com.unifit.unifit.data.mappers

import android.net.Uri
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.unifit.unifit.data.remote.dto.FitnessCategoryDto
import com.unifit.unifit.domain.data.FitnessCategory
import com.unifit.unifit.domain.data.FitnessExercise
import com.unifit.unifit.domain.data.FitnessProgramExercises
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

/*fun QueryDocumentSnapshot.toFitnessExercise() : FitnessExercise {
    return FitnessExercise(
        name = getString("name") ?: "",
        time = getString("time") ?: ""

    )
}*/

fun FitnessCategoryDto.toFitnessCategory(): FitnessCategory {
    return FitnessCategory(
        name = name,
        imageUri = imageUri
    )
}