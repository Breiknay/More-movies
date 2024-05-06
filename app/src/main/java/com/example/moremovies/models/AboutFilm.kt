package com.example.moremovies.models

data class AboutFilm(
    val posterUrlPreview: String,
    val nameOriginal: String,
    val ratingKinopoisk: Int,
    val filmLength: Int,
    val year: Int,
    val description: String,
    val url: String
)