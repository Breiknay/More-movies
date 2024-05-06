package com.example.moremovies.screen.premieres_film

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moremovies.network.model_request.film.FilmsRequest
import com.example.moremovies.network.model_request_premieres.Month
import com.example.moremovies.network.model_request_premieres.RequestPremieres
import com.example.moremovies.screen.film_list_screen.FilmListAction
import com.example.moremovies.usecase.GetInfoFilmUseCase
import com.example.moremovies.usecase.GetPremieresFilmUseCase
import com.example.moremovies.usecase.GetVideosFilmUseCase

import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject


@HiltViewModel

class PremieresFilmViewModel @Inject constructor(
    private val getPremieresFilmUseCase: GetPremieresFilmUseCase,
    private val getVideosFilmUseCase: GetVideosFilmUseCase
) : ViewModel() {

    val state = MutableStateFlow(
        PremieresFilmState(emptyList())
    )


     val requestPremieres = MutableLiveData<RequestPremieres>()


    init {
        processAction(PremieresFilmAction.InitPremieresFilm(getLocalDate()))
        requestPremieres.postValue(getLocalDate())
    }

    fun processAction(action: PremieresFilmAction) {
        viewModelScope.launch {
            val result: Flow<PremieresFilmResult> = when (action) {
                is PremieresFilmAction.InitPremieresFilm ->
                    getPremieresFilmUseCase.getPremieresFilm(action.requestPremieres)

                is PremieresFilmAction.ShowVideoPremieresFilm ->
                    getVideosFilmUseCase.getVideoFilm(action.idFilm)
            }
            result.collect {
                processResult(it)
            }
        }
    }

    private suspend fun processResult(result: PremieresFilmResult) {
        val prevState = state.value
        when (result) {
            is PremieresFilmResult.PremieresFilmList -> {
                state.emit(
                    prevState.copy(
                        listFilm = result.premieresFilmList,
                        error = null,
                        urlStringForVideo = ""
                    )
                )
            }

            is PremieresFilmResult.Error -> {
                state.emit(prevState.copy(error = result.error))
            }

            is PremieresFilmResult.Loading -> {
                state.emit(prevState.copy(loading = result.isLoading))
            }

            is PremieresFilmResult.LoadingVideos -> {
                state.emit(prevState.copy(loadingVideos = result.isLoadingVideos))
            }

            is PremieresFilmResult.PremieresFilUrlStringForVideo -> {
                state.emit(prevState.copy(urlStringForVideo = result.urlStringForVideo))
            }
        }
    }


    private fun getLocalDate(): RequestPremieres {
        val currentDate = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDate.now()
        } else {
            return RequestPremieres(Month.JANUARY, 2024)
        }
        val currentYear = currentDate.year
        val currentMonth = Month.fromNumber(currentDate.monthValue)
        return RequestPremieres(currentMonth, currentYear)
    }

}