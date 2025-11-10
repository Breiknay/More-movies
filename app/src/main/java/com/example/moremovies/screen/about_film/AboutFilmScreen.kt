package com.example.moremovies.screen.about_film

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.moremovies.R
import com.example.moremovies.screen.fitures.CustomAppBar
import com.example.moremovies.screen.fitures.ErrorView
import com.example.moremovies.ui.theme.MainColor
import com.example.moremovies.ui.theme.WhiteColor
import com.example.moremovies.ui.theme.styleTextBodyBig
import com.example.moremovies.ui.theme.styleTextBodyNormal
import com.example.moremovies.utils.parseResponse.hourMinutes
import androidx.core.net.toUri

@Composable
fun AboutFilmScreen(
    state: State<AboutFilmState>,
    id: Int?,
    processAction: (action: AboutFilmAction) -> Unit,
    onNavigate: () -> Unit,
) {
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { }

    val filmListState = state.value
    val values = LocalContext.current
    LaunchedEffect(true) {
        id?.let { AboutFilmAction.InitAboutFilm(it) }?.let { processAction(it) }
    }
    Scaffold(
        topBar = {
            CustomAppBar(
                drawerState = null,
                title = values.getString(R.string.aboutFilm),
                onNavigate = {
                    onNavigate()
                })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val share = Intent.createChooser(Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(
                            Intent.EXTRA_TEXT,
                            filmListState.aboutFilm?.nameOriginal
                        )

                        putExtra(
                            Intent.EXTRA_TITLE,
                            values.getString(R.string.app_name)
                        )

                        data =
                            filmListState.aboutFilm?.url?.toUri()
                        flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                    }, null)
                    launcher.launch(share)
                },
                contentColor = Color.Red
            ) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "FUB Button"
                )
            }

        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MainColor)
                .padding(paddingValues)

        ) {

            if (filmListState.loading) {
                val strokeWidth = 5.dp
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(32.dp),
                        color = Color.LightGray,
                        strokeWidth = strokeWidth
                    )
                }
            } else if (filmListState.error != null) {
                ErrorView(
                    filmListState.error,
                    values.getString(R.string.refreshAboutFilm)
                ) {
                    id?.let { AboutFilmAction.InitAboutFilm(it) }?.let { processAction(it) }
                }
            } else {
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    val painter = rememberAsyncImagePainter(
                        model = filmListState.aboutFilm?.posterUrlPreview
                    )
                    Image(
                        painter = painter,
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(350.dp),
                        contentScale = ContentScale.Crop,
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 10.dp, end = 20.dp, top = 10.dp, bottom = 20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = filmListState.aboutFilm?.nameOriginal ?: "",
                            modifier = Modifier.padding(top = 10.dp),
                            color = WhiteColor,
                            style = styleTextBodyBig
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp, top = 10.dp)
                        ) {
                            Column(Modifier.weight(0.5f)) {
                                SubtitlePrimary(
                                    text = filmListState.aboutFilm?.ratingKinopoisk
                                        .toString(),
                                )
                                SubtitleSecondary(
                                    text = values.getString(R.string.filterChooseRating)
                                )
                            }
                            Column(Modifier.weight(1f)) {
                                filmListState.aboutFilm?.filmLength?.hourMinutes()?.let {
                                    SubtitlePrimary(
                                        text = it
                                    )
                                }
                                SubtitleSecondary(
                                    text = values.getString(R.string.duration)
                                )
                            }
                            Column(Modifier.weight(0.5f)) {
                                SubtitlePrimary(
                                    text = filmListState.aboutFilm?.year.toString()
                                )
                                SubtitleSecondary(
                                    text = values.getString(R.string.year)
                                )
                            }
                        }
                        Text(
                            text = values.getString(R.string.description),
                            color = WhiteColor,
                            style = styleTextBodyBig
                        )
                        Text(
                            color = WhiteColor,
                            text = filmListState.aboutFilm?.description.toString()
                        )

                    }
                }
            }

        }

    }
}


@Composable
fun SubtitlePrimary(text: String) {
    Text(
        text = text,
        color = WhiteColor,
        style = styleTextBodyBig
    )
}

@Composable
fun SubtitleSecondary(text: String) {
    Text(
        text = text,
        color = WhiteColor,
        style = styleTextBodyNormal
    )
}