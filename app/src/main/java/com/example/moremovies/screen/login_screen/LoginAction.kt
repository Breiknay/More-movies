package com.example.moremovies.screen.login_screen


sealed class LoginAction {
    data class ClickLoginButton(val loginModel: LoginModel) :
        LoginAction()
}

data class LoginModel(
    val email: String,
    val password: String,
)