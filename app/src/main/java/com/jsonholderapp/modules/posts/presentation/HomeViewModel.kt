package com.jsonholderapp.modules.posts.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jsonholderapp.modules.posts.data.PostsDataItem
import com.jsonholderapp.modules.posts.domain.PostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(postsUseCase: PostsUseCase) : ViewModel() {
    val items: Flow<PagingData<PostsDataItem>> =
        postsUseCase.getItems().cachedIn(viewModelScope)
}