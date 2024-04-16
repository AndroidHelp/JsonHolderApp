package com.jsonholderapp.modules.posts.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jsonholderapp.modules.posts.data.PostsDataItem
import com.jsonholderapp.modules.posts.domain.PostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val postsUseCase: PostsUseCase) : ViewModel() {

    private val _postDetails = MutableStateFlow<PostsDataItem?>(null)
    val postDetails: StateFlow<PostsDataItem?> get() = _postDetails

    private val _errorException = MutableStateFlow<String?>(null)
    val errorException: StateFlow<String?> get() = _errorException

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun getPostDetails(postId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            postsUseCase.getPostDetails(postId = postId).collect {
                it.data?.let { data ->
                    _postDetails.value = data
                }
                it.errorMessage?.let { error ->
                    _errorException.value = error
                }
            }
            _isLoading.value = false
        }
    }
}