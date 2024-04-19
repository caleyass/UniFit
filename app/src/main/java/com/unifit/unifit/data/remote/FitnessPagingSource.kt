package com.unifit.unifit.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await
class FitnessPagingSource<T : Any>(
    private val query: Query,
    private val mapper: (QueryDocumentSnapshot) -> T
) : PagingSource<QuerySnapshot, T>() {

    override fun getRefreshKey(state: PagingState<QuerySnapshot, T>): QuerySnapshot? {
        return null
    }

    override suspend fun load(params: LoadParams<QuerySnapshot>): LoadResult<QuerySnapshot, T> {
        return try {
            val currentPage = params.key ?: query.get().await()
            val lastVisibleProduct = currentPage.documents[currentPage.size() - 1]
            val nextPage = query.startAfter(lastVisibleProduct).get().await()

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
}