package com.jsonholderapp.modules.posts.domain

import androidx.paging.PagingData
import com.jsonholderapp.modules.posts.data.PostsRepository
import com.jsonholderapp.modules.posts.data.PostsDataItem
import com.jsonholderapp.network.BaseResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostsUseCaseImpl @Inject constructor(private val postsRepository: PostsRepository) :
    PostsUseCase {
    override fun getItems(): Flow<PagingData<PostsDataItem>> {
        return postsRepository.getPosts()
    }

    override fun getPostDetails(postId: Int): Flow<BaseResponse<PostsDataItem>> {
        return postsRepository.getPostDetails(postId = postId)
    }
}