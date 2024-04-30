package com.example.moremovies.screen.login_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moremovies.usecase.AuthFireBaseUseCase

import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel

class LoginViewModel @Inject constructor(
    private val authFireBaseUseCase: AuthFireBaseUseCase,
) : ViewModel() {

    val state = MutableStateFlow(
        LoginState()
    )

    fun processAction(action: LoginAction) {
        viewModelScope.launch {
            val result: Flow<LoginResult> = when (action) {
                is LoginAction.ClickLoginButton ->
                    authFireBaseUseCase.login(action.loginModel)
            }
            result.collect {
                processResult(it)
            }
        }
    }

    private suspend fun processResult(result: LoginResult) {
        val prevState = state.value
        when (result) {
            is LoginResult.SuccessLogin -> {
                state.emit(
                    prevState.copy(
                        successLogin = result.successLogin,
                        error = null,
                    )
                )
            }

            is LoginResult.Error -> {
                state.emit(prevState.copy(error = result.error))
            }

            is LoginResult.Loading -> {
                state.emit(prevState.copy(loading = result.isLoading))
            }

        }
    }
}

