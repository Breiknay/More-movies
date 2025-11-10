package com.example.moremovies.screen.registration_screen

sealed class RegistrationAction {
    data class ClickRegistrationButton(val registrationModel: RegistrationModel) :
        RegistrationAction();
    class ClickOnDismiss() :
        RegistrationAction()
}

data class RegistrationModel(
    val name: String,
    val email: String,
    val password: String,
)