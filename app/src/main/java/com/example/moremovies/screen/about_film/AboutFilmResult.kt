package com.example.moremovies.screen.about_film

import com.example.moremovies.models.AboutFilm
import com.example.moremovies.network.model_response.film.InfoResponseFilm

sealed class AboutFilmResult {
    data class InfoFilm(val aboutFilm: AboutFilm) : AboutFilmResult()
    data class Error(val error: String) : AboutFilmResult()
    data class Loading(val isLoading: Boolean) : AboutFilmResult()
}