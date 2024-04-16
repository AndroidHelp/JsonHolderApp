package com.jsonholderapp.modules.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.jsonholderapp.modules.posts.details.DetailsScreen
import com.jsonholderapp.modules.posts.presentation.HomeScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(
    navHostController: NavHostController,
    defaultTitle: String
) {
    var topBarTitle by remember { mutableStateOf(defaultTitle) }
    var showBackArrow by remember { mutableStateOf(false) }
    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = topBarTitle) },
            navigationIcon = {
                if (showBackArrow) {
                    IconButton(onClick = {
                        navHostController.navigateUp()
                        topBarTitle = defaultTitle
                        showBackArrow = false
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                navigationIconContentColor = Color.White,
                titleContentColor = MaterialTheme.colorScheme.onPrimary,
                actionIconContentColor = Color.White
            )
        )
    }) { paddingValues ->
        NavHost(
            navController = navHostController,
            startDestination = AppScreens.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(AppScreens.Home.route) {
                HomeScreen({
                    it?.let { id ->
                        navHostController.navigate(AppScreens.Details.route + "/${id}")
                    }
                }) { title, showArrow ->
                    topBarTitle = title
                    showBackArrow = showArrow
                }
            }
            composable(
                AppScreens.Details.route + "/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("id") ?: 0
                DetailsScreen(id) { title, showArrow ->
                    topBarTitle = title
                    showBackArrow = showArrow
                }
            }
        }
    }
}
