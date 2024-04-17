package com.example.moremovies.screen.movie_list_screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun FilmListDestination(
    navigationController: NavHostController,
    viewModel: FilmListViewModel = hiltViewModel()
) {

    val state = viewModel.state.collectAsState()

    MovieListScreen(state)
}