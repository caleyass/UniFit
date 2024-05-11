package com.unifit.unifit.domain.data

data class User(
    val userId: String,
    val email : String,
    val name: String,
    val age: Int,
    val gender: String,
    val weight : Int,
    val height: Int,
    val bodyLevel: String,
    val desiredWeight: Int,
)
