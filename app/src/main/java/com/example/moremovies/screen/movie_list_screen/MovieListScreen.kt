package com.example.moremovies.screen.movie_list_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter

import com.example.moremovies.models.Genre
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarStyle


@Composable
fun MovieListScreen(state: State<FilmListState>) {

    when (val filmListState = state.value) {

        else -> {
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

            } else {
                LazyColumn {
                    items(filmListState.listFilm.size) { index ->
                        val film = filmListState.listFilm[index]
                        film.rating?.let {
                            AlbumCard(
                                albumName = film.nameOriginal,
                                genres = film.genres,
                                albumYear = film.year,
                                rating = it.toFloat(),
                                imageUrl = film.posterUrlPreview,

                                )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun AlbumCard(
    albumName: String,
    genres: List<Genre>?,
    albumYear: Int?,
    rating: Float,
    imageUrl: String, // Add a parameter for the image URL
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            // Image section
            Image(
                painter = rememberImagePainter(imageUrl),
                contentDescription = "Album cover for $albumName",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f) // Maintain aspect ratio (optional)
            )
            Spacer(
                modifier = Modifier
                    .height(8.dp)
                    .width(8.dp)
            )

            // Text section (unchanged)
            Text(
                text = albumName,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "$albumYear",
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(16.dp))



            RatingBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                numOfStars = 5,
                size = 16.dp,
                spaceBetween = 3.dp,
                value = rating,
                style = RatingBarStyle.Fill(),
                onValueChange = { },
                onRatingChanged = {}
            )
        }
    }
}
