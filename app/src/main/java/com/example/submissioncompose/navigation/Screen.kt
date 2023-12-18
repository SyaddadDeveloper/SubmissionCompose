package com.example.submissioncompose.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object DetailGame : Screen("home/{gameID}") {
        fun createRoute(gameId: String) = "home/$gameId"
    }
    object Favorite : Screen("favorite")
    object Profile : Screen("about")
}