package com.example.moremovies.screen.login_screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun LoginDestination(
    navigationController: NavHostController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()
    LoginScreen(
        state,
        processAction = {
            viewModel.processAction(it)
        },
        onNavigate = {
            navigationController.navigate(it) {
                popUpTo(navigationController.graph.startDestinationId)
                launchSingleTop = true
            }
        })
}


