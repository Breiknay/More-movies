package com.example.moremovies.usecase

import com.example.moremovies.models.Film
import com.example.moremovies.models.Genre
import com.example.moremovies.network.model_request.film.FilmsRequest
import com.example.moremovies.repository.FilmRepository
import com.example.moremovies.screen.movie_list_screen.FilmListResult
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoadListFilmUseCase @Inject constructor(
    private val repository: FilmRepository,
) {

    suspend fun getListFilm(filmRequest: FilmsRequest): Flow<FilmListResult> = flow {
        emit(FilmListResult.Loading(true))
        try {
            println(Gson().toJson(filmRequest))
            val response = repository.getFilms(filmRequest)
            if (response.isSuccessful) {
                response.body()?.items?.map {
                    Film(
                        it.kinopoiskId,
                        it.posterUrlPreview ?: it.posterUrl ?: "",
                        it.nameOriginal ?: it.nameRu ?: it.nameEn ?: "",
                        it.ratingImdb, it.year,
                        it.genres?.map {
                            Genre(
                                it.genre
                            )
                        },
                    )

                }?.run {
                    emit(FilmListResult.FilmList(this))
                }

            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown Error"
                emit(FilmListResult.Error(errorBody))
            }
            emit(FilmListResult.Loading(false))

        } catch (error: Exception) {
            emit(FilmListResult.Error(error.message.toString()))
            emit(FilmListResult.Loading(false))
        }

    }
}


