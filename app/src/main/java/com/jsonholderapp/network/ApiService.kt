package com.jsonholderapp.network

import com.jsonholderapp.modules.posts.data.PostsDataItem
import com.jsonholderapp.network.ApiEndPoints.Companion.URL_POSTS
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET(URL_POSTS)
    suspend fun getPosts(
        @Query("_start") start: Int,
        @Query("_limit") limit: Int = 10
    ): Response<List<PostsDataItem>>

    @GET("$URL_POSTS/{id}")
    suspend fun getPostDetails(@Path("id") postId: Int): Response<PostsDataItem>
}