package com.unifit.unifit.domain.data

enum class FitnessWorkoutPart {
    WARM_UP,
    MAIN,
    COOL_DOWN;

    fun toStringValue(): String {
        return when (this) {
            WARM_UP -> "Warm Up"
            MAIN -> "Main"
            COOL_DOWN -> "Cool Down"
        }
    }


}
