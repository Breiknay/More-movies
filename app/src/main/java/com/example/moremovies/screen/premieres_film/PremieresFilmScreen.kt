package com.example.moremovies.screen.premieres_film

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.moremovies.R
import com.example.moremovies.network.model_request_premieres.RequestPremieres
import com.example.moremovies.screen.fitures.CustomAppBar
import com.example.moremovies.screen.fitures.ErrorView
import com.example.moremovies.ui.theme.MainColor

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PremieresFilmScreen(
    drawerState: DrawerState,
    state: State<PremieresFilmState>,
    requestPremieres: RequestPremieres?,
    processAction: (action: PremieresFilmAction) -> Unit,
    onNavigate: () -> Unit,
) {
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { }
    val filmListState = state.value
    val values = LocalContext.current

    Scaffold(
        topBar = {
            CustomAppBar(
                drawerState = drawerState,
                title = values.getString(R.string.premieres),
                onNavigate = {
                    onNavigate()
                })
        },
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
                    filmListState.error, values.getString(R.string.refreshAboutFilm)
                ) {
                    requestPremieres?.let { PremieresFilmAction.InitPremieresFilm(it) }
                        ?.let { processAction(it) }
                }
            } else {


                Box(
                    contentAlignment = Alignment.Center
                ) {

                    if (filmListState.loadingVideos) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Bottom
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(32.dp),
                                color = Color.LightGray,
                                strokeWidth = 5.dp
                            )
                        }
                    }


                    val pagerState = rememberPagerState(pageCount = {
                        filmListState.listFilm.size
                    })
                    HorizontalPager(
                        modifier = Modifier.fillMaxHeight(),
                        pageSpacing = 15.dp,
                        contentPadding = PaddingValues(horizontal = 40.dp),

                        state = pagerState
                    ) { index ->
                        BannerItem(image = filmListState.listFilm[index].posterUrlPreview) {
                            processAction(PremieresFilmAction.ShowVideoPremieresFilm(filmListState.listFilm[index].id))
                        }

                        if (filmListState.urlStringForVideo != "") {
                            val uris = Uri.parse(filmListState.urlStringForVideo)
                            val intents = Intent(Intent.ACTION_VIEW, uris)
                            val b = Bundle()
                            b.putBoolean("new_window", true)
                            intents.putExtras(b)
                            launcher.launch(intents)
                            filmListState.urlStringForVideo = ""
                        }

                    }
                }


            }

        }

    }

}


@Composable
fun BannerItem(image: String, onNavigate: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        val model = ImageRequest.Builder(LocalContext.current).data(image).size(Size.ORIGINAL)
            .crossfade(true).build()
        val painter = rememberAsyncImagePainter(model)
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onNavigate()
                },
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )

    }
}
