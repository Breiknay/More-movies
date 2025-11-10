package com.example.moremovies.repository


import com.example.moremovies.network.Api
import com.example.moremovies.network.model_request.film.FilmsRequest
import com.example.moremovies.network.model_request_premieres.RequestPremieres
import com.example.moremovies.network.model_response.film.InfoResponseFilm
import retrofit2.Response
import javax.inject.Inject

class FilmRepository @Inject constructor(private val api: Api) {
    suspend fun getFilms(request: FilmsRequest) = api.getFilms(
        request.toMap()
    )

    suspend fun getInfoFilm(id: Int): Response<InfoResponseFilm> {
        return api.getInfoFilm(id)
    }
    suspend fun getVideoFilm(id: Int) = api.getVideoFilm(id)
    suspend fun getFilmsSearch(name: String, page: Int) = api.getFilmsSearch(name, page)

    suspend fun getFilmsPremieres(requestPremieres: RequestPremieres) =
        api.getFilmsPremieres(requestPremieres.toMap())
}