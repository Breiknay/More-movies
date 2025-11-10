package com.example.moremovies.screen.registration_screen


sealed class RegistrationState {
    class SuccessRegistration() : RegistrationState()
    data class RegistrationStateError(val error: String) : RegistrationState()
    class Loading() : RegistrationState()
    class InitialState : RegistrationState()
}
