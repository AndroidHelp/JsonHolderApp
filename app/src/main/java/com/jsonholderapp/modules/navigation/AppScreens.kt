package com.jsonholderapp.modules.navigation

sealed class AppScreens(val route: String) {
    data object Home : AppScreens("/home")
    data object Details : AppScreens("/details")
}