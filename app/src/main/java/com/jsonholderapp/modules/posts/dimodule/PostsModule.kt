package com.jsonholderapp.modules.posts.dimodule

import com.jsonholderapp.modules.posts.data.PostsRepository
import com.jsonholderapp.modules.posts.data.PostsRepositoryImpl
import com.jsonholderapp.modules.posts.data.PostsPagingSource
import com.jsonholderapp.modules.posts.domain.PostsUseCase
import com.jsonholderapp.modules.posts.domain.PostsUseCaseImpl
import com.jsonholderapp.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PostsModule {

    @Singleton
    @Provides
    fun providePaging(apiService: ApiService): PostsPagingSource = PostsPagingSource(apiService)

    @Singleton
    @Provides
    fun provideRepository(
        postsPagingSource: PostsPagingSource,
        apiService: ApiService
    ): PostsRepository = PostsRepositoryImpl(postsPagingSource, apiService)

    @Singleton
    @Provides
    fun provideDataUseCase(postsRepository: PostsRepository): PostsUseCase =
        PostsUseCaseImpl(postsRepository)
}