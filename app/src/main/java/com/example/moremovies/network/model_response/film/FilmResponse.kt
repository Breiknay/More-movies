package com.example.moremovies.network.model_response.film

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("total") val total: Int,
    @SerializedName("totalPages") val totalPages: Int,
    @SerializedName("items") val items: List<Movie>
)

data class Movie(
    @SerializedName("kinopoiskId") val kinopoiskId: Int,
    @SerializedName("imdbId") val imdbId: String?,
    @SerializedName("nameRu") val nameRu: String?,
    @SerializedName("nameEn") val nameEn: String?,
    @SerializedName("nameOriginal") val nameOriginal: String?,
    @SerializedName("countries") val countries: List<Country>?,
    @SerializedName("genres") val genres: List<Genre>?,
    @SerializedName("ratingKinopoisk") val ratingKinopoisk: Double?,
    @SerializedName("ratingImdb") val ratingImdb: Double?,
    @SerializedName("year") val year: Int?,
    @SerializedName("type") val type: String?,
    @SerializedName("posterUrl") val posterUrl: String?,
    @SerializedName("posterUrlPreview") val posterUrlPreview: String?
)

data class Country(
    @SerializedName("country") val country: String?
)

data class Genre(
    @SerializedName("genre") val genre: String?
)