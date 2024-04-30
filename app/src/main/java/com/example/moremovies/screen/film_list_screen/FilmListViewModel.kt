package com.example.moremovies.screen.film_list_screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.moremovies.models.Film
import com.example.moremovies.network.model_request.film.FilmsRequest
import com.example.moremovies.repository.FilmRepository

import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilmListViewModel @Inject constructor(
    private val repository: FilmRepository,
) : ViewModel() {

    private val filmsRequest = MutableLiveData<FilmsRequest>()

    fun init(request: FilmsRequest) {
        if (request != filmsRequest.value) {
            processAction(FilmListAction.InitLoadFilm(filmRequest = request))
            filmsRequest.postValue(request)
        }
    }

    private val _filmResponse: MutableStateFlow<PagingData<Film>> =
        MutableStateFlow(PagingData.empty())
    var filmResponse = _filmResponse.asStateFlow()
        private set

    fun processAction(action: FilmListAction) {
        viewModelScope.launch {
            when (action) {
                is FilmListAction.InitLoadFilm ->
                    fetchFilms(action.filmRequest, null)

                is FilmListAction.SearchFilm ->
                    fetchSearchFilms(action.filmName)
            }

        }
    }

    private fun fetchSearchFilms(nameFilm: String) {
        viewModelScope.launch {
            Pager(
                config = PagingConfig(
                    5, enablePlaceholders = false
                )
            ) {
//                filmPagingSource.setFilmRequest(FilmsRequest())
                val filmPagingSource = FilmSearchPagingSource(repository, nameFilm)
                filmPagingSource
            }.flow.cachedIn(viewModelScope).collect {
                _filmResponse.value = it
            }
        }
    }

    private fun fetchFilms(filmsRequest: FilmsRequest, nameFilm: String?) {
        viewModelScope.launch {
            Pager(
                config = PagingConfig(
                    5, enablePlaceholders = false
                )
            ) {
                if (nameFilm != null) {
                    filmsRequest.keyword = nameFilm
                }
                val filmPagingSource = FilmPagingSource(repository, filmsRequest)
                filmPagingSource
            }.flow.cachedIn(viewModelScope).collect {
                _filmResponse.value = it
            }
        }
    }


//    private suspend fun processResult(result: FilmListResult) {
//        val prevState = state.value
//        when (result) {
//            is FilmListResult.FilmList -> {
//                state.emit(
//                    prevState.copy(
//                        error = null,
//                        listFilm = result.list,
//                        totalPages = result.page
//                    )
//                )
//            }
//
//            is FilmListResult.Error -> {
//                state.emit(prevState.copy(error = result.error))
//            }
//
//            is FilmListResult.Loading -> {
//                state.emit(prevState.copy(loading = result.isLoading))
//            }
//
//
//        }
//    }


}