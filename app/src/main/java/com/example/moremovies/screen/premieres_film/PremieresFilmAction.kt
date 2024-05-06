package com.example.moremovies.screen.premieres_film

import com.example.moremovies.network.model_request_premieres.RequestPremieres


sealed class PremieresFilmAction {
    data class InitPremieresFilm(val requestPremieres: RequestPremieres) : PremieresFilmAction()
    data class ShowVideoPremieresFilm(val idFilm: Int) : PremieresFilmAction()
}