package com.example.moremovies.models

import com.google.gson.annotations.SerializedName


data class Film(
    val id: Int,
    val posterUrlPreview: String,
    val nameOriginal: String,
    val rating: Double?,
    val year: Int?,
    val genres: List<Genre>?,
)

data class Genre(
    val genre: String?
)



