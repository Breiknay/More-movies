package com.example.moremovies.screen.movie_list_screen

import com.example.moremovies.models.Film


data class FilmListState(
    val listFilm: List<Film>,
    val loading: Boolean = false,
    val error: String? = null
)