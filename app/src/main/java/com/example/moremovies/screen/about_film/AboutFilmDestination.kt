package com.example.moremovies.screen.about_film

import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.moremovies.network.model_request.film.FilmsRequest

@Composable
fun AboutFilmDestination(
    navigationController: NavHostController,
    id: Int?,
    viewModel: AboutFilmViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()
    AboutFilmScreen(state, id, processAction = {
        viewModel.processAction(it)
    }, onNavigate = {
        navigationController.navigateUp()
    })
}