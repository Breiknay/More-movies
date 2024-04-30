package com.example.moremovies.screen.about_film


sealed class AboutFilmAction {
    data class InitAboutFilm(val idFilm: Int) : AboutFilmAction()
}