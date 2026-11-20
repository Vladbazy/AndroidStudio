package com.example.rickandmorty.ui.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.rickandmorty.RickAndMortyApplication
import com.example.rickandmorty.data.CharactersRepository
import com.example.rickandmorty.model.Character
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

// Состояние экрана «Персонажи»
data class CharactersUiState(
    val characters: List<Character> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    // true, когда загружена последняя страница
    val endReached: Boolean = false
)

class CharactersViewModel(
    private val repository: CharactersRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CharactersUiState())
    val uiState: StateFlow<CharactersUiState> = _uiState.asStateFlow()

    // номер страницы, которая будет загружена следующей
    private var nextPage = 1

    init {
        loadNextPage()
    }

    fun loadNextPage() {
        val state = _uiState.value
        // не грузим повторно, пока идёт загрузка, и после последней страницы
        if (state.isLoading || state.endReached) return

        _uiState.update { it.copy(isLoading = true, isError = false) }
        viewModelScope.launch {
            try {
                val response = repository.getCharacters(nextPage)
                nextPage++
                _uiState.update {
                    it.copy(
                        characters = it.characters + response.results,
                        isLoading = false,
                        endReached = response.info.next == null
                    )
                }
            } catch (e: IOException) {
                _uiState.update { it.copy(isLoading = false, isError = true) }
            } catch (e: HttpException) {
                _uiState.update { it.copy(isLoading = false, isError = true) }
            }
        }
    }

    // Фабрика передаёт репозиторий из контейнера в конструктор ViewModel
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as RickAndMortyApplication)
                CharactersViewModel(application.container.charactersRepository)
            }
        }
    }
}