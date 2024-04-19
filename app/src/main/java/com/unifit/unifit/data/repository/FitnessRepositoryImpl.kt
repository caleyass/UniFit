package com.unifit.unifit.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.unifit.unifit.data.local.APP_CONSTANTS
import com.unifit.unifit.data.mappers.toFitnessCategory
import com.unifit.unifit.data.mappers.toFitnessWorkout
import com.unifit.unifit.data.remote.FirebaseApi
import com.unifit.unifit.data.remote.FitnessPagingSource
import com.unifit.unifit.di.AppModule
import com.unifit.unifit.domain.data.FitnessCategory
import com.unifit.unifit.domain.data.FitnessWorkout
import com.unifit.unifit.domain.repositories.FitnessRepository
import kotlinx.coroutines.flow.Flow
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

}
