package com.example.moremovies.usecase

import com.example.moremovies.repository.SharedPreferencesRepository
import com.example.moremovies.screen.login_screen.LoginModel
import com.example.moremovies.screen.login_screen.LoginResult
import com.example.moremovies.screen.registration_screen.RegistrationModel
import com.example.moremovies.screen.registration_screen.RegistrationResult
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthFireBaseUseCase @Inject constructor(
    private val authPrefs: SharedPreferencesRepository
) {
    suspend fun registration(registrationModel: RegistrationModel): Flow<RegistrationResult> =
        flow {
            emit(RegistrationResult.Loading(true))
            try {
                val result = Firebase.auth.createUserWithEmailAndPassword(
                    registrationModel.email,
                    registrationModel.password
                ).await()
                println(result)
                emit(
                    RegistrationResult.SuccessRegistration(true)
                )

                emit(RegistrationResult.Loading(false))

            } catch (error: Exception) {
                emit(RegistrationResult.Error(error.message.toString()))
                emit(RegistrationResult.Loading(false))
            }

        }

    suspend fun login(loginModel: LoginModel): Flow<LoginResult> =
        flow {
            emit(LoginResult.Loading(true))
            try {
                Firebase.auth.signInWithEmailAndPassword(
                    loginModel.email,
                    loginModel.password
                ).await()

                emit(
                    LoginResult.SuccessLogin(true)
                )

                authPrefs.saveAuthState(true)

                emit(LoginResult.Loading(false))

            } catch (error: Exception) {
                emit(LoginResult.Error(error.message.toString()))
                emit(LoginResult.Loading(false))
            }

        }
}


