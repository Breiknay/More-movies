package com.example.moremovies.screen.registration_screen

import com.example.moremovies.screen.login_screen.LoginResult

sealed class RegistrationResult {
    data class SuccessRegistration(val successRegistration: Boolean) : RegistrationResult()
    data class Error(val error: String) : RegistrationResult()
    data class Loading(val isLoading: Boolean) : RegistrationResult()
}