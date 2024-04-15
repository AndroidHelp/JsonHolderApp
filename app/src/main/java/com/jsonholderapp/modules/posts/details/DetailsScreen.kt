package com.jsonholderapp.modules.posts.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jsonholderapp.R
import com.jsonholderapp.modules.posts.data.PostsDataItem

@Composable
fun DetailsScreen(postId: Int, updateTopBar: (String, Boolean) -> Unit) {

    val detailsViewModel = hiltViewModel<DetailsViewModel>()
    LaunchedEffect(postId) {
        detailsViewModel.getPostDetails(postId = postId)
    }
    println("navigating to details")
    detailsViewModel.postDetails.collectAsState().value?.let {
        updateTopBar(stringResource(id = R.string.title_details, it.id ?: 0), true)
        DetailsScreenUI(it)
    }

    detailsViewModel.errorException.collectAsState().value?.let {
        DetailsScreenErrorUI(it)
    }

    ShowLoading(detailsViewModel.isLoading.collectAsState().value)
}

@Composable
fun ShowLoading(isLoading: Boolean) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isLoading) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun DetailsScreenErrorUI(errorMessage: String) {
    Box(
        contentAlignment = Alignment.Center, modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = errorMessage, color = Color.Red,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(16.dp)
        )
    }

}

@Composable
fun DetailsScreenUI(post: PostsDataItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        post.title?.let { title ->
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        post.body?.let { body ->
            Text(
                text = body,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        post.userId?.let { userId ->
            Text(
                text = "User ID: $userId",
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }
        post.id?.let { id ->
            Text(
                text = "Post ID: $id", style = MaterialTheme.typography.labelSmall
            )
        }
    }
}
