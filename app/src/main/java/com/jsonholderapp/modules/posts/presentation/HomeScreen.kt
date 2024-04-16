package com.jsonholderapp.modules.posts.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.jsonholderapp.R
import com.jsonholderapp.modules.posts.data.PostsDataItem
import com.jsonholderapp.theme.OffWhite

@Composable
fun HomeScreen(
    itemClick: (Int?) -> Unit, updateTopBar: (String, Boolean) -> Unit
) {
    val homeViewModel: HomeViewModel = hiltViewModel()
    val postItems = homeViewModel.items.collectAsLazyPagingItems()
    HomeScreenUi(postItems, itemClick)
    when (postItems.loadState.refresh) {
        LoadState.Loading -> {
            updateTopBar(stringResource(id = R.string.title_home), false)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        }

        is LoadState.Error -> {
            val errorState = postItems.loadState.refresh as LoadState.Error
            Text(
                text = "Error: ${errorState.error.message}",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }

        else -> {

        }
    }
}

@Composable
fun HomeScreenUi(postItems: LazyPagingItems<PostsDataItem>, itemClick: (Int?) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 24.dp)
            .background(OffWhite)
    ) {
        LazyColumn {
            items(postItems.itemCount) { index ->
                Surface(modifier = Modifier
                    .clickable {
                        itemClick(postItems[index]?.id)
                    }
                    .fillMaxWidth()
                    .padding(16.dp),
                    shape = MaterialTheme.shapes.medium,
                    color = Color.White,
                    shadowElevation = 4.dp) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "ID: ${postItems[index]?.id ?: ""}",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Gray
                        )
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 6.dp),
                            text = "Title: ${postItems[index]?.title ?: ""}",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}
