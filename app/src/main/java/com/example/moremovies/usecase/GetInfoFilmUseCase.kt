package com.example.moremovies.usecase

import com.example.moremovies.models.AboutFilm
import com.example.moremovies.repository.FilmRepository
import com.example.moremovies.screen.about_film.AboutFilmResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetInfoFilmUseCase @Inject constructor(
    private val repository: FilmRepository,
) {

    suspend fun getInfoFilm(idFilm: Int): Flow<AboutFilmResult> =
        flow {
            emit(AboutFilmResult.Loading(true))
            try {
                val response = repository.getInfoFilm(idFilm)
                if (response.isSuccessful) {
                    response.body().let {
                        AboutFilm(
                            posterUrlPreview = it?.posterUrlPreview ?: it?.posterUrl ?: it?.logoUrl
                            ?: "",
                            description = it?.description ?: it?.shortDescription ?: "",
                            filmLength = it?.filmLength ?: 0,
                            nameOriginal = it?.nameRu ?: it?.nameOriginal ?: it?.nameEn ?: "",
                            ratingKinopoisk = it?.ratingKinopoisk?.toInt() ?: it?.ratingAwaitCount
                            ?: it?.ratingKinopoiskVoteCount ?: it?.ratingGoodReviewVoteCount ?: 0,
                            year = it?.year ?: it?.startYear ?: 0,
                            url = it?.webUrl.toString()
                        )

                    }.run {
                        emit(AboutFilmResult.InfoFilm(this))
                    }
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Unknown Error"
                    emit(AboutFilmResult.Error(errorBody))
                }
                emit(AboutFilmResult.Loading(false))

            } catch (error: Exception) {
                emit(AboutFilmResult.Error(error.message.toString()))
                emit(AboutFilmResult.Loading(false))
            }

        }
}


