package com.unifit.unifit.di

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.unifit.unifit.data.local.dao.AlarmDao
import com.unifit.unifit.data.local.dao.AnalysisDao
import com.unifit.unifit.data.local.dao.PillDao
import com.unifit.unifit.data.local.dao.SleepDao
import com.unifit.unifit.data.local.database.MyDatabase
import com.unifit.unifit.data.remote.FirebaseApi
import com.unifit.unifit.data.repository.AnalysisRepositoryImpl
import com.unifit.unifit.data.repository.FitnessRepositoryImpl
import com.unifit.unifit.data.repository.PillRepositoryImpl
import com.unifit.unifit.domain.repositories.AnalysisRepository
import com.unifit.unifit.domain.repositories.FitnessRepository
import com.unifit.unifit.domain.repositories.PillRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFitnessDatabase(@ApplicationContext context : Context): MyDatabase {
        return MyDatabase.getDatabase(context)
    }

/*    @Provides
    @Singleton
    fun provideFitnessExercisesDao(db: MyDatabase): FitnessExercisesDao {
        return db.fitnessExercisesDao
    }*/

    @Provides
    @Singleton
    fun provideFirebaseFirestoreDb(): FirebaseFirestore {
        val db = Firebase.firestore
        db.firestoreSettings =  FirebaseFirestoreSettings.Builder()
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
    fun provideFitnessRepository(firebaseApi: FirebaseApi): FitnessRepository {
        return FitnessRepositoryImpl(firebaseApi)
    }

    @Provides
    @Singleton
    fun provideAnalysisRepository(analysisDao: AnalysisDao): AnalysisRepository {
        return AnalysisRepositoryImpl(analysisDao)
    }

    @Provides
    @Singleton
    fun providePillRepository(pillDao: PillDao): PillRepository {
        return PillRepositoryImpl(pillDao)
    }

    @Provides
    @Singleton
    fun provideAlarmDao(db: MyDatabase): AlarmDao {
        return db.alarmDao
    }

    @Provides
    @Singleton
    fun providePillDao(db: MyDatabase): PillDao {
        return db.pillDao
    }

    @Provides
    @Singleton
    fun provideSleepDao(db: MyDatabase): SleepDao {
        return db.sleepDao
    }

    @Provides
    @Singleton
    fun provideAnalysisDao(db: MyDatabase): AnalysisDao {
        return db.analysisDao
    }

    @Provides
    @Singleton
    fun provideFirebaseStorageReference(): StorageReference {
        return FirebaseStorage.getInstance().reference
    }


}