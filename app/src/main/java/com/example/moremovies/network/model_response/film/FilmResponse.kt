package com.example.moremovies.network.model_response.film

import com.google.gson.annotations.SerializedName

data class FilmResponse(
    val total: Int,
    val totalPages: Int,
    @SerializedName("items")
    val listFilm: List<Movie>
)

data class Movie(
    val kinopoiskId: Int,
    val imdbId: String?,
    val nameRu: String?,
    val nameEn: String?,
    val nameOriginal: String?,
    val countries: List<Country>?,
    val genres: List<Genre>?,
    val ratingKinopoisk: Double?,
    val ratingImdb: Double?,
    val year: Int?,
    val type: String?,
    val posterUrl: String?,
    val posterUrlPreview: String?
)

data class Country(
    val country: String?
)

data class Genre(
    val genre: String?
)