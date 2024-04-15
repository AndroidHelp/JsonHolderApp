package com.jsonholderapp.modules.posts.domain

import androidx.paging.PagingData
import com.jsonholderapp.modules.posts.data.PostsDataItem
import com.jsonholderapp.network.BaseResponse
import kotlinx.coroutines.flow.Flow

interface PostsUseCase {
    fun getItems(): Flow<PagingData<PostsDataItem>>
    fun getPostDetails(postId: Int): Flow<BaseResponse<PostsDataItem>>
}