package com.example.rickandmorty.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// Ответ сервера со страницей персонажей
@Serializable
data class CharactersResponse(
    val info: PageInfo,
    val results: List<Character>
)

// Информация о страницах; next = null, если страница последняя
@Serializable
data class PageInfo(
    val count: Int,
    val pages: Int,
    val next: String? = null,
    val prev: String? = null
)

@Serializable
data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String = "",
    val gender: String,
    val origin: Place,
    val location: Place,
    val image: String,
    // ссылки на серии, в которых появлялся персонаж
    val episode: List<String>
)

@Serializable
data class Place(
    val name: String,
    val url: String = ""
)

@Serializable
data class Episode(
    val id: Int,
    val name: String,
    @SerialName("air_date")
    val airDate: String,
    @SerialName("episode")
    val code: String
)