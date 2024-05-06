package com.example.moremovies.screen.about_film

import com.example.moremovies.models.AboutFilm

data class AboutFilmState(
    val aboutFilm: AboutFilm? = null,
    val loading: Boolean = false,
    val error: String? = null
)