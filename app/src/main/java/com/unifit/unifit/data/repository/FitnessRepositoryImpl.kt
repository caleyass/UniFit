package com.unifit.unifit.data.repository

import android.net.Uri
import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.unifit.unifit.data.local.APP_CONSTANTS
import com.unifit.unifit.data.mappers.toFitnessCategory
import com.unifit.unifit.data.mappers.toFitnessWorkout
import com.unifit.unifit.data.remote.FirebaseApi
import com.unifit.unifit.data.remote.paging.paging_source.FitnessPagingSource
import com.unifit.unifit.data.utils.Resource
import com.unifit.unifit.domain.data.FitnessCategory
import com.unifit.unifit.domain.data.FitnessExercise
import com.unifit.unifit.domain.data.FitnessWorkout
import com.unifit.unifit.domain.repositories.FitnessRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FitnessRepositoryImpl @Inject constructor(
    private val firebaseApi: FirebaseApi,
) : FitnessRepository {

    override fun getFitnessProgramWorkouts(categoryName : String): Flow<PagingData<FitnessWorkout>> {
        val q = firebaseApi.getFitnessProgramsWorkouts(categoryName)
        return Pager(
            config = PagingConfig(pageSize = APP_CONSTANTS.PAGE_SIZE),
            pagingSourceFactory = {
                FitnessPagingSource(
                    query = q,
                    mapper = QueryDocumentSnapshot::toFitnessWorkout
                )
            }
        ).flow
    }

    override fun getFitnessCategories(): Flow<PagingData<FitnessCategory>> {
        val q = firebaseApi.getFitnessCategories()
        return Pager(
            config = PagingConfig(pageSize = APP_CONSTANTS.PAGE_SIZE),
            pagingSourceFactory = {
                FitnessPagingSource(
                    query = q,
                    mapper = QueryDocumentSnapshot::toFitnessCategory
                )
            }
        ).flow
    }

    override fun getFitnessProgramExercise(
        category: String,
        nameOfExercise: String,
        nameOfWorkoutPart: String,
        index: Int
    ): Flow<Resource<FitnessExercise>> = flow {
        try {
            firebaseApi.getFitnessProgramExercise(
                category,
                nameOfExercise,
                nameOfWorkoutPart,
                index
            )
                .get()
                .await()
                .forEach { documentSnapshot ->
                    val name = documentSnapshot.getString("name") ?: ""
                    val gifUri = Uri.parse(documentSnapshot.getString("gif"))
                    val time = documentSnapshot.getLong("time") ?: 0
                    Log.d("Firebase", "$name $gifUri $time")
                    val fitnessExercise = FitnessExercise(gifUri, name, time)
                    emit(Resource.Success(fitnessExercise))
                }
        } catch (e: Exception) {
            Log.e("Firebase", "Error getting document: ", e)
            emit(Resource.Error(throwable = e))
        }
    }

    override fun getFitnessProgramExercises(
        category: String,
        nameOfWorkout: String,
        nameOfWorkoutPart: String
    ): Flow<Resource<List<FitnessExercise>>> = flow {
        try {
            val listFitnessExercises = mutableListOf<FitnessExercise>()
            firebaseApi.getFitnessProgramExercises(
                category,
                nameOfWorkout,
                nameOfWorkoutPart
            )
            .forEach {
                it
                .get()
                .await()
                .map { documentSnapshot ->
                    val name = documentSnapshot.getString("name") ?: ""
                    val gifUri = Uri.parse(documentSnapshot.getString("gif"))
                    val time = documentSnapshot.getLong("time") ?: 0
                    Log.d("Firebase", "fitness exercises $name $gifUri $time")
                    listFitnessExercises.add(FitnessExercise(gifUri, name, time))
                }
            }
            emit(Resource.Success(listFitnessExercises))
        } catch (e: Exception) {
            Log.e("Firebase", "Error getting document: ", e)
            emit(Resource.Error(throwable = e))
        }
    }

    /*override suspend fun getFitnessProgramExercise(
        category: String,
        nameOfWorkoutPart: String,
        index:Int
    ): FitnessExercise? {
        var fitnessExercise: FitnessExercise ? = null
        try {
            firebaseApi.getFitnessProgramExercise()
                .get()
                .await()
                .map { documentSnapshot ->
                    Log.d(
                        "Firebase",
                        "${documentSnapshot.data["name"]} ${documentSnapshot.data["gif"]}"
                    )
                    fitnessExercise = FitnessExercise(
                        name = documentSnapshot.data["name"].toString(),
                        gif = Uri.parse(documentSnapshot.data["gif"].toString()),
                        time = documentSnapshot.data["time"].toString().toLong()
                    )

                    return@map
                    //TODO ADD TO DATABASE FITNESS EXERCISE
                }
        }
        catch(e: Exception){
            Log.d("Firebase", "Error getting document: ", e)
        }
        return fitnessExercise

    }*/

//        return networkBoundResource(
//            query = {
//                fitnessExercisesDao.getExercises(category, nameOfWorkoutPart).first()
//            },
//            fetch = {
//                firebaseApi.getWorkoutExercisesByWorkout(category, nameOfWorkoutPart)
//            },
//            saveFetchResult = { exercises ->
//                val fitnessEntity = FitnessExercisesEntity()
//                fitnessEntity.exercisesQueries = exercises
//                fitnessEntity.category = category
//                fitnessEntity.part = nameOfWorkoutPart
//                fitnessExercisesDao.upsertExercises(fitnessEntity)
//            }
//        )
        //firebaseApi.getWorkoutExercisesByWorkout(category, nameOfWorkoutPart)

//    }

}
