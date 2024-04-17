package com.unifit.unifit.data.remote


import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import com.unifit.unifit.data.local.APP_CONSTANTS
import com.unifit.unifit.data.remote.dto.FitnessCategory
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
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
    private val categoriesDocument = firestoreDatabase.collection(APP_CONSTANTS.Companion.FIREBASE_CONSTANTS.COLLECTION_FITNESS_PROGRAM_NAME)
        .document(APP_CONSTANTS.Companion.FIREBASE_CONSTANTS.DOCUMENT_FITNESS_PROGRAM_CATEGORIES_NAME)

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

    fun getFitnessProgramWorkouts(categoryName:String = "Abdomen") : Flow<List<FitnessCategory>> = callbackFlow {
        categoriesDocument
            .collection(categoryName)
            .get()
            .addOnSuccessListener{ result ->
                trySend(result.documents.map { document ->
                    FitnessCategory(
                        name = document.id,
                        imageResource = null
                    )
                })
            }
            .addOnFailureListener {
                Log.e("Firebase", "fetchDataFromFirebase: ${it.message}")
                cancel(CancellationException())
            }
        awaitClose { close()}
    }

    fun getFitnessPrograms() : Flow<List<FitnessCategory>> = callbackFlow {
        categoriesDocument
            .get()
            .addOnSuccessListener { result ->
                CoroutineScope(Dispatchers.Main).launch {
                    val list = mutableListOf<FitnessCategory>()
                    val deferredList = result.data?.map { entry ->
                        async {
                            val imageResource = getImage("fitness_program/${entry.key}.jpg")
                            FitnessCategory(name = entry.key, imageResource = imageResource)
                        }
                    }
                    deferredList?.let {
                        list.addAll(it.awaitAll())
                    }
                    list.forEach {
                        Log.d("Firebase", "fetchDataFromFirebase: ${it.name} ${it.imageResource}")
                    }
                    trySend(list)
                }
            }
            .addOnFailureListener {
                cancel(CancellationException())
            }
        awaitClose { close()}
    }

/*    private suspend fun collectListOfImages(result: DocumentSnapshot) : List<FitnessProgram> {

        return list
    }*/
}
