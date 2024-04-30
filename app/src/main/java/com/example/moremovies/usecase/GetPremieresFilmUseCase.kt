package com.example.moremovies.usecase

import com.example.moremovies.models.AboutFilm
import com.example.moremovies.models.Film
import com.example.moremovies.network.model_request_premieres.RequestPremieres
import com.example.moremovies.network.model_response.film.Movie
import com.example.moremovies.repository.FilmRepository
import com.example.moremovies.screen.about_film.AboutFilmResult
import com.example.moremovies.screen.premieres_film.PremieresFilmResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPremieresFilmUseCase @Inject constructor(
    private val repository: FilmRepository,
) {

    suspend fun getPremieresFilm(requestPremieres: RequestPremieres): Flow<PremieresFilmResult> =
        flow {
            emit(PremieresFilmResult.Loading(true))
            try {
                val response = repository.getFilmsPremieres(requestPremieres)
                if (response.isSuccessful) {

                    val movieResponse = response.body()?.items?.map {
                        Film(
                            id = it.kinopoiskId,
                            nameOriginal = it.nameRu ?: it.nameEn ?: it.nameRu ?: "",
                            posterUrlPreview = it.posterUrl ?: it.posterUrlPreview ?: "",
                            year = it.year ?: 0,
                            rating = null
                        )
                    }

                    emit(PremieresFilmResult.PremieresFilmList(movieResponse ?: emptyList()))
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Unknown Error"
                    emit(PremieresFilmResult.Error(errorBody))
                }
                emit(PremieresFilmResult.Loading(false))

            } catch (error: Exception) {
                emit(PremieresFilmResult.Error(error.message.toString()))
                emit(PremieresFilmResult.Loading(false))
            }

        }
}


