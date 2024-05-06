package com.example.moremovies.network.model_response_search_film

import com.example.moremovies.network.model_response.film.Country
import com.example.moremovies.network.model_response.film.Genre

data class FilmSearch(
    val filmId: Int,
    val nameRu: String?,
    val nameEn: String?,
    val type: String?,
    val year: String?,
    val description: String?,
    val filmLength: String?,
    val countries: List<Country>?,
    val genres: List<Genre>?,
    val rating: String?,
    val ratingVoteCount: Int?,
    val posterUrl: String?,
    val posterUrlPreview: String?
)





data class SearchFilmResponse(
    val keyword: String,
    val pagesCount: Int,
    val searchFilmsCountResult: Int,
    val films: List<FilmSearch>
)
