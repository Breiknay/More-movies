package com.example.moremovies.network.model_request.film

import android.os.Parcelable
import androidx.annotation.StringRes
import com.example.moremovies.R
import kotlinx.parcelize.Parcelize

interface LocalizedName {
    @get:StringRes
    val stringValueRes: Int
}

enum class OrderTypeForFilter(override val stringValueRes: Int) : LocalizedName {
    RATING(R.string.order_rating),
    NUM_VOTE(R.string.order_num_vote),
    YEAR(R.string.order_year),
    ALL(R.string.order_all)
}

enum class MovieType(override val stringValueRes: Int) : LocalizedName {
    FILM(R.string.movie_film),
    TV_SHOW(R.string.movie_tv_show),
    TV_SERIES(R.string.movie_tv_series),
    MINI_SERIES(R.string.movie_mini_series),
    ALL(R.string.movie_all)
}

@Parcelize
data class FilmsRequest(
    var order: OrderTypeForFilter = OrderTypeForFilter.RATING,
    var type: MovieType = MovieType.ALL,
    var ratingFrom: Int = 0,
    var ratingTo: Int = 10,
    var yearFrom: Int = 1990,
    var yearTo: Int = 2024,
    var page: Int = 1,
    var keyword: String? = null
) : Parcelable {


    fun toMap(): Map<String, String> {
        return mapOf(
            "order" to order.name,
            "type" to type.name,
            "ratingFrom" to ratingFrom.toString(),
            "ratingTo" to ratingTo.toString(),
            "yearFrom" to yearFrom.toString(),
            "yearTo" to yearTo.toString(),
            "page" to page.toString(),
        )
    }


    override fun hashCode(): Int {
        var result = order.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + ratingFrom
        result = 31 * result + ratingTo
        result = 31 * result + yearFrom
        result = 31 * result + yearTo
        result = 31 * result + page
        result = 31 * result + (keyword?.hashCode() ?: 0)
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FilmsRequest

        if (order != other.order) return false
        if (type != other.type) return false
        if (ratingFrom != other.ratingFrom) return false
        if (ratingTo != other.ratingTo) return false
        if (yearFrom != other.yearFrom) return false
        if (yearTo != other.yearTo) return false
        if (page != other.page) return false
        return keyword == other.keyword
    }


}

