package com.example.moremovies.screen.movie_list_screen

import com.example.moremovies.models.Film

sealed class FilmListResult {

    data class FilmList(val list: List<Film>) : FilmListResult()
    data class Error(val error: String) : FilmListResult()
    data class Loading(val isLoading: Boolean) : FilmListResult()
}
