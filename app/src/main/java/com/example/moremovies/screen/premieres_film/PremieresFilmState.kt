package com.example.moremovies.screen.premieres_film

import com.example.moremovies.models.AboutFilm
import com.example.moremovies.models.Film

data class PremieresFilmState(
    val listFilm: List<Film>,
    var urlStringForVideo: String = "",
    val loadingVideos: Boolean = false,
    val loading: Boolean = false,
    val error: String? = null
)