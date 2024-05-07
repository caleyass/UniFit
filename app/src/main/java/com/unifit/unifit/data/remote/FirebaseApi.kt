package com.unifit.unifit.data.remote


import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.Source
import com.google.firebase.storage.StorageReference
import com.unifit.unifit.data.local.APP_CONSTANTS.Companion.COLLECTION_FITNESS_PROGRAM_NAME
import com.unifit.unifit.data.local.APP_CONSTANTS.Companion.DOCUMENT_FITNESS_PROGRAM_CATEGORIES_NAME
import com.unifit.unifit.data.local.APP_CONSTANTS.Companion.NAME_FIELD
import com.unifit.unifit.data.local.APP_CONSTANTS.Companion.PAGE_SIZE
import com.unifit.unifit.domain.data.FitnessExercise
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FirebaseApi @Inject constructor(
    private val firestoreDatabase: FirebaseFirestore,
    private val storageReference: StorageReference
) {
    private val categoriesDocument = firestoreDatabase.collection(COLLECTION_FITNESS_PROGRAM_NAME)
        .document(DOCUMENT_FITNESS_PROGRAM_CATEGORIES_NAME)

    /**
     * Gets fitness categories of workouts
     * */
    fun getFitnessCategories() : Query {
        return categoriesDocument
            .collection("categories")
            .orderBy(NAME_FIELD)
            .limit(PAGE_SIZE.toLong())

    }
    /**
     * Gets fitness program's workouts
     * */
    fun getFitnessProgramsWorkouts(categoryName:String) : Query{
        return categoriesDocument
            .collection(categoryName)
            .orderBy(NAME_FIELD)
            .limit(PAGE_SIZE.toLong())
    }

    fun getFitnessProgramWorkout(categoryName:String, workoutId : String): DocumentReference {
        return categoriesDocument
            .collection(categoryName)
            .document(workoutId)
    }
    /**
     * Get all exercises of workout:
     * List<Query> - each executes getWorkoutExercise
     * name
     * gif
     * time
     * */
    suspend fun getWorkoutExercisesQueries(
        category:String = "Abdomen",
        nameOfWorkout: String = "Ab Burn Circuit",
        nameOfWorkoutPart: String = "Warm-up"
    ) : List<Query> = suspendCoroutine { continuation ->
        val listOfQueries = mutableListOf<Query>()
        getFitnessProgramWorkout(category, nameOfWorkout)
            .get(Source.CACHE)
            .addOnSuccessListener {
                //TODO deserealize it to list
                val list = it.get(nameOfWorkoutPart).toString()
                list.substring(1, list.length - 1).split(", ").forEach {
                    val query = getWorkoutExercise(nameOfWorkoutPart, it)
                    Log.d("Firebase", "ref ${it}")
                    listOfQueries.add(query)
                }
                continuation.resume(listOfQueries)
            }
            .addOnFailureListener {
                Log.d("Firebase", "Error getting documents: ", it)
                continuation.resumeWithException(it)
            }
    }
    /**
     * Gets workout exercise in document "nameOfWorkoutPart" that contains fields:
     * name
     * gif
     * time
     * getWorkoutExercise("Warm-up", "March in place") gets the exercise "March in place" in the document "Warm-up"
     * */
    fun getWorkoutExercise(nameOfWorkoutPart: String = "Warm-up", nameOfExercise:String = "March in place"): Query {
        return firestoreDatabase.collection(COLLECTION_FITNESS_PROGRAM_NAME)
            .document(nameOfWorkoutPart)
            .collection("exercises")
            .whereEqualTo(NAME_FIELD, nameOfExercise)
            .orderBy(NAME_FIELD)
    }


    /*suspend fun getWorkoutExercisesQueries(
        category:String = "Abdomen",
        nameOfWorkoutPart: String = "Warm-up"
    ) : List<Query>{
        val listOfQueries = mutableListOf<Query>()
        getFitnessProgramsWorkouts(category)
            .get(Source.CACHE)
            .await()
            .map { documentSnapshot ->
                val d = documentSnapshot.data[nameOfWorkoutPart].toString()
                if (d != "null") {
                    d.substring(1, d.length - 1).split(", ").forEach {
                        val query = getWorkoutExercise(nameOfWorkoutPart = nameOfWorkoutPart, nameOfExercise = it)
                        listOfQueries.add(query)
                    }
                }
            }
        return listOfQueries
    }*/

    suspend fun getFitnessProgramExercise(
        category: String = "Abdomen",
        nameOfWorkout: String = "Ab Burn Circuit",
        nameOfWorkoutPart: String = "Warm-up",
        index:Int = 2
    ) : Query {
        return getWorkoutExercisesQueries(
            category = category,
            nameOfWorkout = nameOfWorkout,
            nameOfWorkoutPart = nameOfWorkoutPart
        )[index]
    }

    suspend fun getFitnessProgramExercises(
        category: String = "Abdomen",
        nameOfWorkout: String = "Ab Burn Circuit",
        nameOfWorkoutPart: String = "Warm-up"
    ) : List<Query> {
        return getWorkoutExercisesQueries(
            category = category,
            nameOfWorkout = nameOfWorkout,
            nameOfWorkoutPart = nameOfWorkoutPart
        )
    }
}
