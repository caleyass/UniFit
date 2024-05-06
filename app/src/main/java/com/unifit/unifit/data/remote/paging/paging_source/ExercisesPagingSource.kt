/*
package com.unifit.unifit.data.remote.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.Source
import com.unifit.unifit.domain.data.FitnessExercise
import kotlinx.coroutines.tasks.await

class ExercisesPagingSource(
    private val queryWarmUp : List<Query>,
    private val queryMainWorkout : Query,
    private val queryCoolDown : List<Query>,
) : PagingSource<QuerySnapshot, FitnessExercise>() {
    override fun getRefreshKey(state: PagingState<QuerySnapshot, FitnessExercise>): QuerySnapshot? {
        return null
    }

    override suspend fun load(params: LoadParams<QuerySnapshot>): LoadResult<QuerySnapshot, FitnessExercise> {
        return try {
            val currentPage = params.key ?:
            queryWarmUp.forEach { query ->
                query.get(Source.CACHE).await()
            }
            val lastVisibleProduct = currentPage[]
            val nextPage = query.startAfter(lastVisibleProduct).get(Source.CACHE).await()
            Log.d("Firebase", "load: currentPage is from cache ${currentPage.metadata.isFromCache}")
            Log.d("Firebase", "load: nextPage is from cache ${nextPage.metadata.isFromCache}")

            val listItems = mutableListOf<T>()
            currentPage.map { documentSnapshot ->
                listItems.add(mapper(documentSnapshot))
            }

            LoadResult.Page(
                data = listItems,
                prevKey = null,
                nextKey = nextPage
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}*/
