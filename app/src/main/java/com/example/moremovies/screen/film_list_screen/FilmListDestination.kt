package com.example.moremovies.screen.film_list_screen

import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.moremovies.network.model_request.film.FilmsRequest

@Composable
fun FilmListDestination(
    navigationController: NavHostController,
    drawerState: DrawerState,
    filmsRequest: FilmsRequest?,
    viewModel: FilmListViewModel = hiltViewModel()
) {

    if (filmsRequest != null) {
        viewModel.init(filmsRequest)
    }

    val response = viewModel.filmResponse.collectAsLazyPagingItems()
    FilmListScreen(drawerState, response, filmsRequest, {
        navigationController.navigate(it)
    }, {
        viewModel.processAction(it)
    })
}