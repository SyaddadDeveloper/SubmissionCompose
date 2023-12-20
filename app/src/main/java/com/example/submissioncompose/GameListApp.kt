package com.example.submissioncompose

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.submissioncompose.navigation.BottomBarItem
import com.example.submissioncompose.navigation.Screen
import com.example.submissioncompose.ui.screen.detail.DetailScreen
import com.example.submissioncompose.ui.screen.favorite.FavoriteScreen
import com.example.submissioncompose.ui.screen.home.HomeScreen
import com.example.submissioncompose.ui.screen.profile.ProfileScreen
import com.example.submissioncompose.ui.screen.splash.SplashScreen

@Composable
fun GameListApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {

    Scaffold(
        modifier = modifier,
        bottomBar = {
            if (!isCurrentRouteSplash(navController.currentBackStackEntryAsState().value?.destination?.route)) {
                BottomBar(navController = navController)
            }
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Splash.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = Screen.Splash.route) {
                SplashScreen(navController = navController)
            }

            composable(
                route = Screen.Home.route
            ) {
                HomeScreen(navigateToDetail = { gameId ->
                    navController.navigate(Screen.DetailGame.createRoute(gameId))
                })
            }

            composable(
                route = Screen.DetailGame.route,
                arguments = listOf(navArgument("gameId") { type = NavType.StringType })
            ) {
                val id = it.arguments?.getString("gameId") ?: ""
                DetailScreen(
                    gameId = id,
                    navigateBack = { navController.navigateUp() }
                )
            }

            composable(
                route = Screen.Favorite.route
            ) {
                val context = LocalContext.current
                FavoriteScreen(
                    navigateBack = { navController.navigateUp() },
                    navigateToDetail = { gameId ->
                        navController.navigate(Screen.DetailGame.createRoute(gameId))
                    },
                    onOrderButtonClicked = { message ->
                        shareOrder(context, message)
                    }
                )
            }

            composable(
                route = Screen.Profile.route
            ) {
                ProfileScreen(
                    navigateBack = { navController.navigateUp() }
                )
            }
        }
    }
}

private fun shareOrder(context: Context, summary: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.share_games))
        putExtra(Intent.EXTRA_TEXT, summary)
    }

    context.startActivity(
        Intent.createChooser(
            intent,
            context.getString(R.string.share_games)
        )
    )
}

@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItems = listOf(
            BottomBarItem(
                title = stringResource(R.string.menu_home),
                icon = ImageVector.vectorResource(id = R.drawable.ic_baseline_home_24),
                screen = Screen.Home
            ),
            BottomBarItem(
                title = stringResource(R.string.menu_favorite),
                icon = ImageVector.vectorResource(id = R.drawable.ic_baseline_favorite_24),
                screen = Screen.Favorite
            ),
            BottomBarItem(
                title = stringResource(R.string.menu_profile),
                icon = ImageVector.vectorResource(id = R.drawable.ic_baseline_account_circle_24),
                screen = Screen.Profile
            ),
        )
        navigationItems.map { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },
                label = { Text(item.title) },
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

private fun isCurrentRouteSplash(route: String?): Boolean {
    return route == Screen.Splash.route
}
