package com.example.moremovies.screen.film_list_screen

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moremovies.models.Film
import com.example.moremovies.network.model_request.film.FilmsRequest
import com.example.moremovies.network.model_response.film.Movie
import com.example.moremovies.repository.FilmRepository
import retrofit2.HttpException
import java.io.IOException
import com.example.moremovies.network.model_response_search_film.SearchFilmResponse

class FilmPagingSource(
    private val repository: FilmRepository,
    private val filmsRequest: FilmsRequest
) : PagingSource<Int, Film>() {

    override fun getRefreshKey(state: PagingState<Int, Film>): Int? = state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Film> {
        val page = params.key ?: 1
        filmsRequest.page = page


        return try {
            val response = repository.getFilms(filmsRequest)
            val movieResponse = response.listFilm.map {
                Film(
                    id = it.kinopoiskId,
                    nameOriginal = it.nameOriginal ?: it.nameEn ?: it.nameRu ?: "",
                    posterUrlPreview = it.posterUrl ?: it.posterUrlPreview ?: "",
                    year = it.year ?: 0,
                    rating = it.ratingImdb ?: it.ratingKinopoisk ?: 0.0
                )
            }
            LoadResult.Page(
                data = movieResponse,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (movieResponse.isEmpty()) null else page.plus(1)
            )
        } catch (e: IOException) {
            LoadResult.Error(
                e
            )
        } catch (e: HttpException) {
            LoadResult.Error(
                e
            )
        }
    }
}
