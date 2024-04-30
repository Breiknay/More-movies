package com.example.moremovies.screen.login_screen


sealed class LoginResult {
    data class SuccessLogin(val successLogin: Boolean) : LoginResult()
    data class Error(val error: String) : LoginResult()
    data class Loading(val isLoading: Boolean) : LoginResult()
}