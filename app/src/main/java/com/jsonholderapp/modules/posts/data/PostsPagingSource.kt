package com.jsonholderapp.modules.posts.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jsonholderapp.network.ApiService
import javax.inject.Inject

class PostsPagingSource @Inject constructor(private val apiService: ApiService) :
    PagingSource<Int, PostsDataItem>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PostsDataItem> {
        val currentPage = params.key ?: 0
        println("Loading page: $currentPage at ${System.currentTimeMillis()}")
        return try {
            val response = apiService.getPosts(currentPage)
            if (response.isSuccessful) {
                if (response.body() != null) {
                    val items = response.body() ?: emptyList()
                    LoadResult.Page(
                        data = items,
                        prevKey = if (currentPage > 0) currentPage - 1 else null,
                        nextKey = if (items.isNotEmpty()) currentPage + 1 else null
                    )
                } else {
                    LoadResult.Error(Exception("Unknown error"))
                }
            } else {
                LoadResult.Error(Exception(response.message()))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PostsDataItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
