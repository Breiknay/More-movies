package com.example.moremovies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.moremovies.navigation.NavGraph
import com.example.moremovies.ui.theme.MainColor
import com.example.moremovies.ui.theme.MoreMoviesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoreMoviesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MainColor
                ) {
                    NavGraph(rememberNavController())
                }
            }
        }
    }
}

