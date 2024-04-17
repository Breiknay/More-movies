package com.example.moremovies.network

import com.example.moremovies.network.model_response.film.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface Api {


    @GET("films")
    suspend fun getFilms(@QueryMap params: Map<String, String>): Response<MovieResponse>
}