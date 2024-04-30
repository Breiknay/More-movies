package com.example.moremovies.screen.film_list_screen

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.moremovies.R
import com.example.moremovies.models.Film
import com.example.moremovies.navigation.MainRoute
import com.example.moremovies.network.model_request.film.FilmsRequest
import com.example.moremovies.screen.fitures.CustomAppBar
import com.example.moremovies.screen.fitures.ErrorView
import com.google.gson.Gson


@Composable
fun FilmListScreen(
    drawerState: DrawerState,
    response: LazyPagingItems<Film>,
    filmsRequest: FilmsRequest?,
    onNavigate: (route: String) -> Unit,
    processAction: (action: FilmListAction) -> Unit
) {
    val values = LocalContext.current

    Scaffold(
        topBar = {
            CustomAppBar(
                drawerState = drawerState,
                title = null, onNavigate = {
                    val json = Uri.encode(Gson().toJson(filmsRequest))

                    onNavigate("${MainRoute.FilterFilm.name}/${json}")
                }, onValueChange = {
                    filmsRequest?.keyword = it;
                    processAction(FilmListAction.SearchFilm(it))
                }
            )


        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.onTertiaryContainer)
                .padding(top = paddingValues.calculateTopPadding(), start = 10.dp, end = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            response.apply {
                when (loadState.refresh) {
                    is LoadState.Loading -> {
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
                    }

                    is LoadState.Error -> {
                        val error = (loadState.refresh as LoadState.Error).error
                        ErrorView(
                            error.message ?: error.toString(),
                            values.getString(R.string.refresh)
                        ) {
                            response.refresh()
                        }
                    }

                    else -> {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier.padding(start = 5.dp, end = 5.dp, top = 10.dp),
                            content =
                            {
                                items(response.itemCount) {
                                    Column(
                                        modifier = Modifier.padding(
                                            start = 5.dp,
                                            end = 5.dp,
                                            top = 5.dp,
                                            bottom = 10.dp
                                        )
                                    ) {
                                        var isImageLoading by remember { mutableStateOf(false) }
                                        val painter = rememberAsyncImagePainter(
                                            model = response[it]?.posterUrlPreview ?: "",
                                        )

                                        isImageLoading = when (painter.state) {
                                            is AsyncImagePainter.State.Loading -> true
                                            else -> false
                                        }

                                        Box(
                                            contentAlignment = Alignment.Center,
                                        ) {
                                            Image(
                                                painter = painter,
                                                contentDescription = "",
                                                modifier = Modifier
                                                    .size(250.dp)
                                                    .clip(RoundedCornerShape(10.dp))
                                                    .clickable {
                                                        onNavigate("${MainRoute.AboutFilm.value}/${response[it]?.id}")
                                                    },
                                                contentScale = ContentScale.Crop,
                                            )

                                            if (isImageLoading) {
                                                CircularProgressIndicator(
                                                    modifier = Modifier
                                                        .padding(
                                                            horizontal = 6.dp,
                                                            vertical = 3.dp
                                                        ),
                                                    color = Color.LightGray,
                                                )
                                            }
                                        }
                                    }

                                }
                                item {
                                    if (loadState.append is LoadState.Error) {
                                        val error = (loadState.append as LoadState.Error).error

                                        Column(
                                            modifier = Modifier.fillMaxSize(),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Center
                                        ) {

                                            Text(
                                                modifier = Modifier
                                                    .padding(8.dp),
                                                text = error.message ?: error.toString(),
                                                textAlign = TextAlign.Center,
                                                color = Color.White
                                            )

                                            Text(
                                                text = values.getString(R.string.refresh),
                                                style = TextStyle(
                                                    color = MaterialTheme.colorScheme.primaryContainer,
                                                    fontSize = 18.sp,
                                                    fontWeight = FontWeight.Normal,
                                                ),
                                                modifier = Modifier
                                                    .clickable { response.refresh() }
                                            )
                                        }
                                    }
                                }
                            })
                    }
                }

            }
        }
    }


}