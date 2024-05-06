package com.example.moremovies.screen.registration_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moremovies.screen.login_screen.LoginAction
import com.example.moremovies.screen.login_screen.LoginResult
import com.example.moremovies.screen.login_screen.LoginState
import com.example.moremovies.usecase.AuthFireBaseUseCase

import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel

class RegistrationViewModel @Inject constructor(
    private val authFireBaseUseCase: AuthFireBaseUseCase,
) : ViewModel() {

    val state = MutableStateFlow(
        RegistrationState()
    )


    fun processAction(action: RegistrationAction) {
        viewModelScope.launch {
            val result: Flow<RegistrationResult> = when (action) {
                is RegistrationAction.ClickRegistrationButton ->
                    authFireBaseUseCase.registration(action.registrationModel)
            }
            result.collect {
                processResult(it)
            }
        }
    }

    private suspend fun processResult(result: RegistrationResult) {
        val prevState = state.value
        when (result) {
            is RegistrationResult.SuccessRegistration -> {
                state.emit(
                    prevState.copy(
                        successRegistration = result.successRegistration,
                        error = null,
                    )
                )
            }

            is RegistrationResult.Error -> {
                state.emit(prevState.copy(error = result.error))
            }

            is RegistrationResult.Loading -> {
                state.emit(prevState.copy(loading = result.isLoading))
            }

        }
    }
}

