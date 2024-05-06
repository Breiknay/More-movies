package com.example.moremovies.network

import com.example.moremovies.network.model_response.film.FilmResponse
import com.example.moremovies.network.model_response.film.InfoResponseFilm
import com.example.moremovies.network.model_response_premieres_film.ResponsePremieresFilm
import com.example.moremovies.network.model_response_search_film.SearchFilmResponse
import com.example.moremovies.network.model_response_videos.VideosResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface Api {


    @GET("api/v2.2/films")
    suspend fun getFilms(@QueryMap params: Map<String, String>): FilmResponse

    @GET("api/v2.2/films/{id}")
    suspend fun getInfoFilm(@Path("id") id: Int): Response<InfoResponseFilm>

    @GET("api/v2.2/films/premieres")
    suspend fun getFilmsPremieres(
        @QueryMap params: Map<String, String>
    ): Response<ResponsePremieresFilm>

    @GET("api/v2.2/films")
    suspend fun getFilmsSearch(
        @Query("keyword") keyword: String,
        @Query("page") page: Int
    ): FilmResponse

    @GET("api/v2.2/films/{id}/videos")
    suspend fun getVideoFilm(@Path("id") id: Int): Response<VideosResponse>

}