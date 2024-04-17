package com.example.moremovies.screen.movie_list_screen

import com.example.moremovies.network.model_request.film.FilmsRequest

sealed class FilmListAction {
    data class LoadFilm(val filmRequest: FilmsRequest) : FilmListAction()

}