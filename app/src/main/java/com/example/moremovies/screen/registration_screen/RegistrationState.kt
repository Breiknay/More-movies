package com.example.moremovies.screen.registration_screen

import com.example.moremovies.models.AboutFilm
import com.example.moremovies.models.Film

data class RegistrationState(
    val successRegistration: Boolean = false,
    val loading: Boolean = false,
    val error: String? = null
)