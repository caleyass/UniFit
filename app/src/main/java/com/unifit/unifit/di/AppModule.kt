package com.unifit.unifit.di

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.unifit.unifit.data.local.APP_CONSTANTS
import com.unifit.unifit.data.local.database.FitnessDatabase
import com.unifit.unifit.data.mappers.toFitnessCategory
import com.unifit.unifit.data.mappers.toFitnessWorkout
import com.unifit.unifit.data.remote.FirebaseApi
import com.unifit.unifit.data.remote.FitnessPagingSource
import com.unifit.unifit.data.repository.FitnessRepositoryImpl
import com.unifit.unifit.domain.data.FitnessCategory
import com.unifit.unifit.domain.data.FitnessWorkout
import com.unifit.unifit.domain.repositories.FitnessRepository
import dagger.Module
import dagger.Provides
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFitnessDatabase(@ApplicationContext context : Context): FitnessDatabase {
        return FitnessDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideFirebaseFirestoreDb(): FirebaseFirestore {
        val db = Firebase.firestore
        db.firestoreSettings =  FirebaseFirestoreSettings.Builder()
            .setCacheSizeBytes(FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED)
            .build()
        return db
    }

    @Provides
    @Singleton
    fun provideFirebaseApi(firebaseFirestore: FirebaseFirestore, firebaseStorageReference: StorageReference): FirebaseApi {
        return FirebaseApi(
            firebaseFirestore,
            firebaseStorageReference
        )
    }

    @Provides
    @Singleton
    fun provideFitnessRepository(firebaseApi: FirebaseApi,
                                 pagerFitnessCategory : Pager<QuerySnapshot, FitnessCategory>,
                                 pagerFitnessWorkout : Pager<QuerySnapshot, FitnessWorkout>
    ): FitnessRepository {
        return FitnessRepositoryImpl(
            firebaseApi,
            pagerFitnessCategory,
            pagerFitnessWorkout
        )
    }

    @Provides
    @Singleton
    fun provideFirebaseStorageReference(): StorageReference {
        return FirebaseStorage.getInstance().reference
    }

    @Provides
    @Singleton
    fun provideFitnessCategoryPager(firebaseApi: FirebaseApi): Pager<QuerySnapshot, FitnessCategory> {
        val q = firebaseApi.getFitnessCategories()
        return Pager(
            config = PagingConfig(pageSize = APP_CONSTANTS.PAGE_SIZE),
            pagingSourceFactory = {
                FitnessPagingSource(
                    query = q,
                    mapper = QueryDocumentSnapshot::toFitnessCategory
                    )
            }
        )
    }

    @Provides
    @Singleton
    fun provideFitnessWorkoutPager(
        firebaseApi: FirebaseApi,
        @Assisted categoryName: String
    ): Pager<QuerySnapshot, FitnessWorkout> {
        val q = firebaseApi.getFitnessProgramsWorkouts(categoryName)
        return Pager(
            config = PagingConfig(pageSize = APP_CONSTANTS.PAGE_SIZE),
            pagingSourceFactory = {
                FitnessPagingSource(
                    query = q,
                    mapper = QueryDocumentSnapshot::toFitnessWorkout
                )
            }
        )
    }
    @AssistedFactory
    interface FitnessWorkoutPagerFactory {
        fun create(categoryName: String): Pager<QuerySnapshot, FitnessWorkout>
    }

}