package com.example.moremovies.utils.parseResponse

import kotlin.time.Duration.Companion.minutes

fun Int.hourMinutes(): String {
    return "${this.minutes.inWholeHours}h ${this % 60}m"
}