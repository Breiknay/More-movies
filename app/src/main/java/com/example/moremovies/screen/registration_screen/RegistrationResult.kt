package com.example.moremovies.screen.registration_screen

sealed class RegistrationResult {
    class SuccessRegistration() : RegistrationResult()
    data class Error(val error: String) : RegistrationResult()
    class Loading() : RegistrationResult()
}