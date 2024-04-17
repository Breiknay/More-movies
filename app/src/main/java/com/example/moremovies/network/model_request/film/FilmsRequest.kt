package com.example.moremovies.network.model_request.film



data class FilmsRequest(
    val order: String = "RATING",
    val type: String = "ALL",
    val ratingFrom: Int = 0,
    val ratingTo: Int = 10,
    val yearFrom: Int = 1000,
    val yearTo: Int = 3000,
    val page: Int = 1
) {
    fun toMap(): Map<String, String> {
        val map = mutableMapOf<String, String>()
        map["order"] = order
        map["type"] = type
        map["ratingFrom"] = ratingFrom.toString()
        map["ratingTo"] = ratingTo.toString()
        map["yearFrom"] = yearFrom.toString()
        map["yearTo"] = yearTo.toString()
        map["page"] = page.toString()
        return map
    }

    fun Map<String, Any>.toFilmsRequest(): FilmsRequest {
        return FilmsRequest(
            order = this["order"] as? String ?: "RATING",
            type = this["type"] as? String ?: "ALL",
            ratingFrom = this["ratingFrom"] as? Int ?: 0,
            ratingTo = this["ratingTo"] as? Int ?: 10,
            yearFrom = this["yearFrom"] as? Int ?: 1000,
            yearTo = this["yearTo"] as? Int ?: 3000,
            page = this["page"] as? Int ?: 1
        )
    }
}

