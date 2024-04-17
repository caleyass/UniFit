package com.unifit.unifit.data.repository

import com.unifit.unifit.data.remote.FirebaseApi
import com.unifit.unifit.data.remote.dto.FitnessCategory
import com.unifit.unifit.domain.repositories.FitnessRepository
import kotlinx.coroutines.flow.Flow

class FitnessRepositoryImpl(
    //private val fitnessProgramDao: FitnessProgramDao,
    private val firebaseApi: FirebaseApi
) : FitnessRepository {


    override fun getFitnessPrograms(): Flow<List<FitnessCategory>> {
        return firebaseApi.getFitnessPrograms()
    }

    override fun getFitnessProgramWorkouts(categoryName : String): Flow<List<FitnessCategory>> {
        return firebaseApi.getFitnessProgramWorkouts()
    }

}
