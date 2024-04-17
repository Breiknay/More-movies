package com.example.moremovies.repository


import com.example.moremovies.network.Api
import com.example.moremovies.network.model_request.film.FilmsRequest

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FilmRepository @Inject constructor(private val api: Api) {
    suspend fun getFilms(request: FilmsRequest) = api.getFilms(
        request.toMap()
    )

}