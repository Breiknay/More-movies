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

class GetVideosFilmUseCase @Inject constructor(
    private val repository: FilmRepository,
) {

    suspend fun getVideoFilm(id: Int): Flow<PremieresFilmResult> =
        flow {
            emit(PremieresFilmResult.LoadingVideos(true))
            try {
                val response = repository.getVideoFilm(id)
                if (response.isSuccessful) {

                    val movieResponse = response.body()?.items?.first()

                    movieResponse?.url?.let {
                        PremieresFilmResult.PremieresFilUrlStringForVideo(
                            it
                        )
                    }?.let { emit(it) }
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Unknown Error"
                    emit(PremieresFilmResult.Error(errorBody))
                }
                emit(PremieresFilmResult.LoadingVideos(false))

            } catch (error: Exception) {
                emit(PremieresFilmResult.Error(error.message.toString()))
                emit(PremieresFilmResult.LoadingVideos(false))
            }

        }
}


