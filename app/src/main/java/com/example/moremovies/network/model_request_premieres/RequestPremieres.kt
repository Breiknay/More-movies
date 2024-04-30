package com.example.moremovies.network.model_request_premieres

import com.example.moremovies.network.model_request.film.OrderTypeForFilter
import java.time.Year

data class RequestPremieres(
    var month: Month,
    var year: Int
) {
    fun toMap(): Map<String, String> {
        return mapOf(
            "month" to month.name,
            "year" to year.toString(),
        )
    }
}

enum class Month(val number: Int) {
    JANUARY(1),
    FEBRUARY(2),
    MARCH(3),
    APRIL(4),
    MAY(5),
    JUNE(6),
    JULY(7),
    AUGUST(8),
    SEPTEMBER(9),
    OCTOBER(10),
    NOVEMBER(11),
    DECEMBER(12);

    companion object {
        fun fromNumber(number: Int): Month {
            return entries.find { it.number == number } ?: JANUARY
        }
    }
}
