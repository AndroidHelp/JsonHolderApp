package com.jsonholderapp.modules.posts.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.jsonholderapp.network.ApiService
import com.jsonholderapp.network.BaseResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PostsRepositoryImpl @Inject constructor(
    private val postsPagingSource: PostsPagingSource,
    private val apiService: ApiService
) : PostsRepository {
    override fun getPosts(): Flow<PagingData<PostsDataItem>> {
        return Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            pagingSourceFactory = { postsPagingSource }).flow
    }

    override fun getPostDetails(postId: Int): Flow<BaseResponse<PostsDataItem>> {
        return flow {
            try {
                val response = apiService.getPostDetails(postId)
                if (response.isSuccessful) {
                    // Emit the post details if the request was successful
                    if (response.body() != null) {
                        emit(
                            BaseResponse(
                                code = response.code(),
                                errorMessage = null,
                                data = response.body()
                            )
                        )
                    } else {
                        emit(
                            BaseResponse(
                                code = response.code(),
                                errorMessage = "Unexpected error!",
                                data = null
                            )
                        )
                    }
                } else {
                    emit(
                        BaseResponse(
                            code = response.code(),
                            errorMessage = "Error fetching post details: ${
                                response.errorBody()?.string()
                            }",
                            data = null
                        )
                    )
                }
            } catch (e: Exception) {
                emit(
                    BaseResponse(
                        code = 0,
                        errorMessage = "Error fetching post details: ${e.message}",
                        data = null
                    )
                )
            }
        }
    }
}