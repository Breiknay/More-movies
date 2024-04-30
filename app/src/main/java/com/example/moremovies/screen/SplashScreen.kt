package com.example.moremovies.screen

import android.net.Uri
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.moremovies.BuildConfig
import com.example.moremovies.R
import com.example.moremovies.navigation.MainRoute
import com.example.moremovies.network.model_request.film.FilmsRequest
import com.example.moremovies.repository.SharedPreferencesRepository
import com.example.moremovies.ui.theme.WhiteColor
import com.google.gson.Gson


@Composable
fun SplashScreen(navigationController: NavHostController) {
    var values = LocalContext.current
    val alpha = remember { Animatable(0f) }
    val authPrefs = SharedPreferencesRepository(values)

    LaunchedEffect(key1 = true) {

        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 2000, easing = FastOutLinearInEasing)
        )
    }

    LaunchedEffect(alpha.value) {

        if (alpha.value == 1f) {
            val startDestination = if (authPrefs.getAuthState()) {
                MainRoute.PremieresFilm.value
            } else {
                MainRoute.LoginScreen.value
            }

            navigationController.navigate(startDestination) {

                popUpTo(navigationController.graph.id) {
                    saveState = true
                }

                launchSingleTop = true
                restoreState = true
            }
        }
    }
    Box(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Image(
            painter = painterResource(id = R.drawable.kinopoisk_icon_main),
            contentDescription = "",
            alignment = Alignment.Center, modifier = Modifier
                .alpha(alpha.value)
                .fillMaxSize()
                .padding(40.dp)
        )

        Text(
            text = LocalContext.current.getString(R.string.app_name),
            style = TextStyle(
                color = WhiteColor,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .align(Alignment.Center)
                .alpha(alpha.value)
                .padding(top = 120.dp)
        )

        Text(
            text = "${values.getString(R.string.version)} - ${BuildConfig.VERSION_NAME}",
            color = WhiteColor,
            textAlign = TextAlign.Center,
            fontSize = 15.sp,
            modifier = Modifier
                .alpha(alpha.value)
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )
    }
}
