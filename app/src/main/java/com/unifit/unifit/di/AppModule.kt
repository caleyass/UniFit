package com.unifit.unifit.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.unifit.unifit.data.remote.FirebaseApi
import com.unifit.unifit.data.repository.FitnessRepositoryImpl
import com.unifit.unifit.domain.repositories.FitnessRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseFirestoreDb(): FirebaseFirestore {
        return Firebase.firestore
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
    fun provideFitnessRepository(firebaseApi: FirebaseApi): FitnessRepository {
        return FitnessRepositoryImpl(firebaseApi)
    }

    @Provides
    @Singleton
    fun provideFirebaseStorageReference(): StorageReference {
        return FirebaseStorage.getInstance().reference
    }
}