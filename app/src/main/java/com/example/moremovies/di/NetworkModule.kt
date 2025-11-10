package com.example.moremovies.di


import android.content.Context
import com.example.moremovies.network.Api
import com.example.moremovies.repository.FilmRepository
import com.example.moremovies.repository.SharedPreferencesRepository
import com.example.moremovies.usecase.AuthFireBaseUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    private val BASE_URL = "https://kinopoiskapiunofficial.tech/"
    private val API_KEY = "7dc45602-67f2-4d52-a60c-0f672502f91a"

    @Provides
    @Singleton
    fun provideApi(): Api {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                    .header("X-API-KEY", API_KEY)
                val request = requestBuilder.build()
                chain.proceed(request)
            }
            .addInterceptor(HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            })
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
    }

    @Module
    @InstallIn(SingletonComponent::class)
    object RepositoryModule {

        @Provides
        fun provideGetUsersUseCase(api: Api): FilmRepository {
            return FilmRepository(api)
        }

        @Provides
        fun provideSharedPreferencesRepository(@ApplicationContext context: Context): SharedPreferencesRepository {
            return SharedPreferencesRepository(context)
        }

        @Provides
        fun provideAuthFireBaseUseCase(sharedPreferencesRepository: SharedPreferencesRepository): AuthFireBaseUseCase {
            return AuthFireBaseUseCase(sharedPreferencesRepository)
        }


    }
}
