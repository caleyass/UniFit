package com.unifit.unifit.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.Query

@Entity(tableName = "fitness_exercises")
class FitnessExercisesEntity {
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
    var category : String = ""
    var part : String = ""
    //var exercisesQueries:List<Query> = listOf()
}