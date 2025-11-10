package com.example.moremovies.screen.registration_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moremovies.usecase.AuthFireBaseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val authFireBaseUseCase: AuthFireBaseUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<RegistrationState>(
        RegistrationState.InitialState()
    )


    val state: StateFlow<RegistrationState> = _state.asStateFlow()


    fun processAction(action: RegistrationAction) {
        viewModelScope.launch {
            val result: Flow<RegistrationResult> = when (action) {
                is RegistrationAction.ClickRegistrationButton ->
                    authFireBaseUseCase.registration(action.registrationModel)
                is RegistrationAction.ClickOnDismiss -> {
                    _state.emit(
                        RegistrationState.InitialState()
                    );
                    return@launch
                }
            }
            result.collect {
                processResult(it)
            }
        }
    }

    private suspend fun processResult(result: RegistrationResult) {
        when (result) {
            is RegistrationResult.Error -> _state.emit(
                RegistrationState.RegistrationStateError(error = result.error)
            );
            is RegistrationResult.Loading -> _state.emit(
                RegistrationState.Loading()
            );
            is RegistrationResult.SuccessRegistration -> _state.emit(
                RegistrationState.SuccessRegistration()
            );
        }


    }
}

