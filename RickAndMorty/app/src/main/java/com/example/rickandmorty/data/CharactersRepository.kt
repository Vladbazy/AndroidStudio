package com.example.rickandmorty.data

import com.example.rickandmorty.model.Character
import com.example.rickandmorty.model.CharactersResponse
import com.example.rickandmorty.model.Episode
import com.example.rickandmorty.network.RickAndMortyApiService

// Репозиторий: единая точка доступа к данным о персонажах
interface CharactersRepository {
    suspend fun getCharacters(page: Int): CharactersResponse
    suspend fun getCharacter(id: Int): Character
    suspend fun getEpisodes(episodeUrls: List<String>): List<Episode>
}

// Реализация, которая получает данные из сети
class NetworkCharactersRepository(
    private val apiService: RickAndMortyApiService
) : CharactersRepository {

    override suspend fun getCharacters(page: Int): CharactersResponse =
        apiService.getCharacters(page)

    override suspend fun getCharacter(id: Int): Character =
        apiService.getCharacter(id)

    override suspend fun getEpisodes(episodeUrls: List<String>): List<Episode> {
        // из ссылки вида ".../episode/28" берём номер серии
        val ids = episodeUrls.map { it.substringAfterLast("/") }
        return when (ids.size) {
            0 -> emptyList()
            1 -> listOf(apiService.getEpisode(ids.first().toInt()))
            else -> apiService.getEpisodes(ids.joinToString(","))
        }
    }
}