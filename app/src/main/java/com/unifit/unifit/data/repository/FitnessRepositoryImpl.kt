package com.unifit.unifit.data.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import com.google.firebase.firestore.QuerySnapshot
import com.unifit.unifit.data.remote.FirebaseApi
import com.unifit.unifit.domain.data.FitnessCategory
import com.unifit.unifit.domain.data.FitnessWorkout
import com.unifit.unifit.domain.repositories.FitnessRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FitnessRepositoryImpl @Inject constructor(
    private val firebaseApi: FirebaseApi,
    private val pagerFitnessCategory: Pager<QuerySnapshot, FitnessCategory>,
    private val pagerFitnessWorkout: Pager<QuerySnapshot, FitnessWorkout>
) : FitnessRepository {

    override fun getFitnessProgramWorkouts(categoryName : String): Flow<PagingData<FitnessWorkout>> {
        return pagerFitnessWorkout.flow
    }

    override fun getFitnessCategories(): Flow<PagingData<FitnessCategory>> {
        return pagerFitnessCategory.flow
    }

}
