package com.example.moremovies.network.model_response_premieres_film

data class ResponsePremieresFilm(
    val total: Int,
    val items: List<MovieItem>
)

data class MovieItem(
    val kinopoiskId: Int,
    val nameRu: String?,
    val nameEn: String?,
    val year: Int?,
    val posterUrl: String?,
    val posterUrlPreview: String?
)