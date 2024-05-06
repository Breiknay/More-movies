package com.example.moremovies.screen.premieres_film

import com.example.moremovies.models.AboutFilm
import com.example.moremovies.models.Film

sealed class PremieresFilmResult {
    data class PremieresFilmList(val premieresFilmList: List<Film>) : PremieresFilmResult()
    data class PremieresFilUrlStringForVideo(val urlStringForVideo: String) : PremieresFilmResult()

    data class LoadingVideos(val isLoadingVideos: Boolean) : PremieresFilmResult()

    data class Error(val error: String) : PremieresFilmResult()
    data class Loading(val isLoading: Boolean) : PremieresFilmResult()
}