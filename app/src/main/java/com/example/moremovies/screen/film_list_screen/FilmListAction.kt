package com.example.moremovies.screen.film_list_screen

import com.example.moremovies.network.model_request.film.FilmsRequest

sealed class FilmListAction {
    data class InitLoadFilm(val filmRequest: FilmsRequest) : FilmListAction()
    data class SearchFilm(val filmName: String) : FilmListAction()

}