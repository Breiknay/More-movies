package com.example.moremovies.screen.premieres_film

import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun PremieresFilmDestination(
    drawerState: DrawerState,
    navigationController: NavHostController,
    viewModel: PremieresFilmViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()
    PremieresFilmScreen(
        drawerState,
        state,
        requestPremieres = viewModel.requestPremieres.value,
        processAction = {
            viewModel.processAction(it)
        },
        onNavigate = {
            navigationController.navigateUp()
        })
}