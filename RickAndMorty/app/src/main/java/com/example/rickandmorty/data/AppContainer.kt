package com.example.rickandmorty.data

import com.example.rickandmorty.network.RickAndMortyApiService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

// Контейнер зависимостей приложения
interface AppContainer {
    val charactersRepository: CharactersRepository
}

class DefaultAppContainer : AppContainer {
    private val baseUrl = "https://rickandmortyapi.com/api/"

    // ignoreUnknownKeys: поля ответа, которых нет в моделях, пропускаются
    private val json = Json { ignoreUnknownKeys = true }

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: RickAndMortyApiService by lazy {
        retrofit.create(RickAndMortyApiService::class.java)
    }

    override val charactersRepository: CharactersRepository by lazy {
        NetworkCharactersRepository(retrofitService)
    }
}