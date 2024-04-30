package com.example.moremovies.screen.login_screen

data class LoginState(
    val successLogin: Boolean = false,
    val loading: Boolean = false,
    val error: String? = null
)