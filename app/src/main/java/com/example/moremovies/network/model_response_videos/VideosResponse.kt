package com.example.moremovies.network.model_response_videos


data class VideosResponse(
    val total: Int,
    val items: List<TrailerItem>
)

data class TrailerItem(
    val url: String,
    val name: String,
    val site: String
)