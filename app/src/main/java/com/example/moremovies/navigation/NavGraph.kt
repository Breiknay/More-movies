package com.example.moremovies.navigation


import android.app.Activity
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.moremovies.R
import com.example.moremovies.network.model_request.film.FilmsRequest
import com.example.moremovies.repository.SharedPreferencesRepository
import com.example.moremovies.screen.SplashScreen
import com.example.moremovies.screen.about_film.AboutFilmDestination
import com.example.moremovies.screen.film_list_screen.FilmListDestination
import com.example.moremovies.screen.filter_film_screen.FilterFilmScreen
import com.example.moremovies.screen.fitures.GradientButton
import com.example.moremovies.screen.login_screen.LoginDestination
import com.example.moremovies.screen.premieres_film.PremieresFilmDestination
import com.example.moremovies.screen.registration_screen.RegistrationDestination
import com.example.moremovies.ui.theme.MainColor
import com.example.moremovies.ui.theme.Orange
import com.example.moremovies.ui.theme.Red
import com.example.moremovies.ui.theme.WhiteColor
import com.example.moremovies.ui.theme.styleTextBodyBig
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

enum class MainRoute(val value: String) {
    PremieresFilm("premieresFilm"),
    Splash("splash"),
    ListFilm("listFilm"),
    FilterFilm("filterFilm"),
    AboutFilm("aboutFilm"),
    LoginScreen("loginPage"),
    RegistrationScreen("registrationScreen")
}


data class DrawerMenu(val icon: ImageVector, val title: String, val route: String)

@Composable
fun menus(): Array<DrawerMenu> {
    return arrayOf(
        DrawerMenu(
            Icons.Filled.Home,
            stringResource(R.string.premieres),
            MainRoute.PremieresFilm.name
        ),

        DrawerMenu(
            Icons.Filled.Search, stringResource(R.string.listFilm), ("${MainRoute.ListFilm.value}/${
                Uri.encode(Gson().toJson(FilmsRequest()))
            }")
        ),
    )
}


@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                DrawerContent(menus()) { route ->
                    coroutineScope.launch {
                        drawerState.close()
                    }

                    navController.navigate(route) {
                        popUpTo(navController.graph.id) {
                            saveState = true
                        }

                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = MainRoute.Splash.value
        ) {
            composable(MainRoute.Splash.value) {
                SplashScreen(navController)
            }

            composable(MainRoute.RegistrationScreen.value) {
                RegistrationDestination(navController)
            }
            composable(MainRoute.LoginScreen.value) {
                LoginDestination(navController)
            }
            composable(
                route = "${MainRoute.ListFilm.value}/{filmsRequest}",
                arguments = listOf(
                    navArgument("filmsRequest") { type = AssetParamType() }
                ),
            ) {
                val filmsRequest = it.arguments?.getParcelable<FilmsRequest>("filmsRequest")
                FilmListDestination(navController, drawerState, filmsRequest)
            }

            composable(MainRoute.PremieresFilm.value) {
                PremieresFilmDestination(drawerState, navController)
            }
            composable(
                route = "${MainRoute.FilterFilm.value}/{filmsRequest}",
                arguments = listOf(

                    navArgument("filmsRequest") {
                        type = AssetParamType()
                    }

                ),
            ) {

                val filmsRequest = it.arguments?.getParcelable<FilmsRequest>("filmsRequest")

                if (filmsRequest != null) {
                    FilterFilmScreen(navController, filmsRequest)
                }
            }
            composable(
                "${MainRoute.AboutFilm.value}/{idFilm}",
                arguments = listOf(
                    navArgument("idFilm") { type = NavType.IntType }
                ),
            ) {
                val filmId: Int = it.arguments?.getInt("idFilm") ?: 0

                AboutFilmDestination(navController, filmId)
            }
        }
    }
}

@Composable
private fun DrawerContent(
    menus: Array<DrawerMenu>,
    onMenuClick: (String) -> Unit,
) {
    val values = LocalContext.current;
    Column(

        modifier = Modifier
            .fillMaxSize()
            .background(MainColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.size(150.dp),
                imageVector = Icons.Filled.AccountCircle,
                contentScale = ContentScale.Crop,
                contentDescription = null,
                colorFilter = ColorFilter.tint(WhiteColor)
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        menus.forEach {

            NavigationDrawerItem(
                label = { Text(text = it.title, style = styleTextBodyBig, color = WhiteColor) },
                icon = {
                    Icon(
                        modifier = Modifier.size(25.dp),
                        imageVector = it.icon,
                        contentDescription = null,
                        tint = WhiteColor,
                    )
                },
                selected = false,
                onClick = {
                    onMenuClick(it.route)
                },
                colors = NavigationDrawerItemDefaults.colors(
                    selectedContainerColor = WhiteColor,
                    unselectedContainerColor = MainColor
                )
            )


        }
        Spacer(modifier = Modifier.weight(1f))

        GradientButton(
            text = values.getString(R.string.closeApp),
            textColor = Color.Black,
            gradient = Brush.horizontalGradient(
                colors = listOf(
                    Orange,
                    Red,
                    Orange
                )
            )
        ) {
         SharedPreferencesRepository(values).saveAuthState(false);
            val activity = (values as? Activity)
            (activity?.finish())
        }

    }


}


class AssetParamType : NavType<FilmsRequest>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): FilmsRequest? {
        return bundle.getParcelable(key)
    }

    override fun parseValue(value: String): FilmsRequest {
        return Gson().fromJson(value, FilmsRequest::class.java)
    }

    override fun put(bundle: Bundle, key: String, value: FilmsRequest) {
        bundle.putParcelable(key, value)
    }
}