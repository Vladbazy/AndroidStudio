package com.example.rickandmorty.network

import com.example.rickandmorty.model.Character
import com.example.rickandmorty.model.CharactersResponse
import com.example.rickandmorty.model.Episode
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyApiService {

    // Страница со списком персонажей
    @GET("character")
    suspend fun getCharacters(@Query("page") page: Int): CharactersResponse

    // Один персонаж по id
    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id: Int): Character

    // Несколько серий одним запросом, ids вида "1,2,3"
    @GET("episode/{ids}")
    suspend fun getEpisodes(@Path("ids") ids: String): List<Episode>

    // Одна серия: если номер один, сервер возвращает объект, а не список
    @GET("episode/{id}")
    suspend fun getEpisode(@Path("id") id: Int): Episode
}