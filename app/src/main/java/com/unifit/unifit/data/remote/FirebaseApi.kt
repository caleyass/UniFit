package com.unifit.unifit.data.remote


import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.StorageReference
import com.unifit.unifit.data.local.APP_CONSTANTS.Companion
import com.unifit.unifit.data.local.APP_CONSTANTS.Companion.COLLECTION_FITNESS_PROGRAM_NAME
import com.unifit.unifit.data.local.APP_CONSTANTS.Companion.DOCUMENT_FITNESS_PROGRAM_CATEGORIES_NAME
import com.unifit.unifit.data.local.APP_CONSTANTS.Companion.PAGE_SIZE
import com.unifit.unifit.data.remote.dto.FitnessCategoryDto
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class FirebaseApi @Inject constructor(
    private val firestoreDatabase: FirebaseFirestore,
    private val storageReference: StorageReference
) {
    private val categoriesDocument = firestoreDatabase.collection(COLLECTION_FITNESS_PROGRAM_NAME)
        .document(DOCUMENT_FITNESS_PROGRAM_CATEGORIES_NAME)

    fun getFitnessCategories() : Query {
        return categoriesDocument
            .collection("categories")
            .orderBy("name")
            .limit(PAGE_SIZE.toLong())
    }

    suspend fun getImage(imageName: String): Uri {
        val deferredUri = CompletableDeferred<Uri>()

        storageReference.child(imageName).downloadUrl
            .addOnSuccessListener {
                Log.d("Firebase", "getImage: $it")
                deferredUri.complete(it)
            }
            .addOnFailureListener {
                deferredUri.completeExceptionally(it)
            }

        return deferredUri.await()
    }

    fun getFitnessProgramsWorkouts(categoryName:String = "Abdomen") : Query{
        return categoriesDocument
            .collection(categoryName)
            .orderBy("name")
            .limit(PAGE_SIZE.toLong())
    }

    /*fun getFitnessProgramWorkouts(categoryName:String = "Abdomen") : Flow<List<FitnessCategoryDto>> = callbackFlow {

        categoriesDocument
            .collection(categoryName)
            .get()
            .addOnSuccessListener{ result ->
                trySend(result.documents.map { document ->
                    FitnessCategoryDto(
                        name = document.id,
                        imageUri = null
                    )
                })
            }
            .addOnFailureListener {
                Log.e("Firebase", "fetchDataFromFirebase: ${it.message}")
                cancel(CancellationException())
            }
        awaitClose { close()}
    }*/
    // Pagination
    /*suspend fun getFitnessPrograms(limit:Long) : List<FitnessCategoryDto>{
        val deferred = CompletableDeferred<List<FitnessCategoryDto>>()


            .addOnCompleteListener { task ->
                last = task.result.documents[task.result.size()-1]["name"].toString()
                Log.d("Firebase", "last: ${last}")
            }
            .addOnSuccessListener { result ->
                result.documents.forEach {
                    Log.d("Firebase", "fetchDataFromFirebase: ${it["name"]}")
                }
            }
            .addOnFailureListener {

            }
        return emptyList()
    }*/



}
