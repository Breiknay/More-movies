package com.example.moremovies.screen.movie_list_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moremovies.network.model_request.film.FilmsRequest
import com.example.moremovies.usecase.LoadListFilmUseCase

import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class FilmListViewModel @Inject constructor(
    private val loadListFilmUseCase: LoadListFilmUseCase,
) : ViewModel() {

    val state = MutableStateFlow(
        FilmListState(emptyList())
    )

    init {
        processAction(FilmListAction.LoadFilm(FilmsRequest()))
    }

    fun processAction(action: FilmListAction) {
        viewModelScope.launch {
            val result: Flow<FilmListResult> = when (action) {
                is FilmListAction.LoadFilm ->
                    loadListFilmUseCase.getListFilm(action.filmRequest)
            }
            result.collect {
                processResult(it)
            }
        }
    }

    private suspend fun processResult(result: FilmListResult) {
        val prevState = state.value
        when (result) {
            is FilmListResult.FilmList -> {
                state.emit(prevState.copy(listFilm = result.list))
            }

            is FilmListResult.Error -> {
                state.emit(prevState.copy(error = result.error))
            }

            is FilmListResult.Loading -> {
                state.emit(prevState.copy(loading = result.isLoading))
            }

        }
    }


}