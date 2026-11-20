package com.example.rickandmorty.ui.character

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.rickandmorty.RickAndMortyApplication
import com.example.rickandmorty.data.CharactersRepository
import com.example.rickandmorty.model.Character
import com.example.rickandmorty.model.Episode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

// Состояние экрана «Персонаж»
sealed interface CharacterUiState {
    object Loading : CharacterUiState
    object Error : CharacterUiState
    data class Success(
        val character: Character,
        val episodes: List<Episode>
    ) : CharacterUiState
}

class CharacterViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: CharactersRepository
) : ViewModel() {

    // id персонажа передаётся из навигации
    private val characterId: Int = checkNotNull(savedStateHandle["characterId"])

    private val _uiState = MutableStateFlow<CharacterUiState>(CharacterUiState.Loading)
    val uiState: StateFlow<CharacterUiState> = _uiState.asStateFlow()

    init {
        loadCharacter()
    }

    fun loadCharacter() {
        _uiState.value = CharacterUiState.Loading
        viewModelScope.launch {
            _uiState.value = try {
                val character = repository.getCharacter(characterId)
                // серии загружаются после получения персонажа
                val episodes = repository.getEpisodes(character.episode)
                CharacterUiState.Success(character, episodes)
            } catch (e: IOException) {
                CharacterUiState.Error
            } catch (e: HttpException) {
                CharacterUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as RickAndMortyApplication)
                CharacterViewModel(
                    savedStateHandle = createSavedStateHandle(),
                    repository = application.container.charactersRepository
                )
            }
        }
    }
}