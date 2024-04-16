package com.jsonholderapp.modules.posts.data

import androidx.paging.PagingData
import com.jsonholderapp.network.BaseResponse
import kotlinx.coroutines.flow.Flow

interface PostsRepository {
    fun getPosts(): Flow<PagingData<PostsDataItem>>
    fun getPostDetails(postId: Int): Flow<BaseResponse<PostsDataItem>>
}