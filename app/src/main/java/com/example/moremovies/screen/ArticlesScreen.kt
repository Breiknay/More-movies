package com.example.moremovies.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.moremovies.navigation.NavGraph
import com.example.moremovies.screen.fitures.CustomAppBar

@Composable
fun ArticlesScreen(drawerState: DrawerState) {
    Scaffold(
        topBar = {
            CustomAppBar(
                drawerState = drawerState,
                title = "Articles"
            )
        }
    ) { paddingValues ->
        Surface(
            color = MaterialTheme.colorScheme.onTertiaryContainer
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = " Articles Screen")
            }
        }
    }
}