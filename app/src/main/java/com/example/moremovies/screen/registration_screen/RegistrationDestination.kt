package com.example.moremovies.screen.registration_screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.moremovies.screen.login_screen.LoginViewModel

@Composable
fun RegistrationDestination(
    navigationController: NavHostController,
    viewModel: RegistrationViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()
    RegistrationScreen(
        state,
        processAction = {
            viewModel.processAction(it)
        },
        onNavigate = {
            navigationController.navigate(it) {
                popUpTo(navigationController.graph.startDestinationId)
            }
        })
}


