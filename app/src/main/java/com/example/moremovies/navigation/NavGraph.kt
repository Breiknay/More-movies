package com.example.moremovies.navigation


import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.moremovies.screen.SplashScreen


const val SPLASH_SCREEN = "splashScreen"


@Composable
fun NavGraph(navController: NavHostController) {

//
    NavHost(navController = navController, startDestination = SPLASH_SCREEN) {
        composable(SPLASH_SCREEN) {
            SplashScreen()
        }

        composable(SPLASH_SCREEN) {
            SplashScreen()
        }


    }
}
