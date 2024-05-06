package com.example.moremovies.screen.about_film

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moremovies.usecase.GetInfoFilmUseCase

import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class AboutFilmViewModel @Inject constructor(
    private val getInfoFilmUseCase: GetInfoFilmUseCase,
) : ViewModel() {

    val state = MutableStateFlow(
        AboutFilmState()
    )


    fun processAction(action: AboutFilmAction) {
        viewModelScope.launch {
            val result: Flow<AboutFilmResult> = when (action) {
                is AboutFilmAction.InitAboutFilm ->
                    getInfoFilmUseCase.getInfoFilm(action.idFilm)
            }
            result.collect {
                processResult(it)
            }
        }
    }

    private suspend fun processResult(result: AboutFilmResult) {
        val prevState = state.value
        when (result) {
            is AboutFilmResult.InfoFilm -> {
                state.emit(prevState.copy(aboutFilm = result.aboutFilm, error = null))
            }

            is AboutFilmResult.Error -> {
                state.emit(prevState.copy(error = result.error))
            }

            is AboutFilmResult.Loading -> {
                state.emit(prevState.copy(loading = result.isLoading))
            }

        }
    }


}