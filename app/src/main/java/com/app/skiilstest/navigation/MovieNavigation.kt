package com.app.skiilstest.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.app.skiilstest.model.MovieItem
import com.app.skiilstest.ui.screens.details.DetailsScreen
import com.app.skiilstest.ui.screens.main.MainScreen
import com.google.gson.Gson

@Composable
fun MovieNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController, startDestination = MovieScreens.MAIN_SCREEN.name
    ) {

        composable(MovieScreens.MAIN_SCREEN.name) {
            MainScreen(navController = navController)
        }

        val route = MovieScreens.DETAILS_SCREEN.name + "/{movieJson}"
        composable(route = route,
            arguments = listOf(navArgument("movieJson") {
                type = NavType.StringType
            })
        ) { navBack ->
            navBack.arguments?.getString("movieJson").let { movie ->
                val movieItemData = Gson().fromJson(movie, MovieItem::class.java)
                DetailsScreen(navController = navController, movieItemData)
            }
        }

    }
}
