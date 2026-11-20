package com.example.artspaceapp.ui

import androidx.lifecycle.ViewModel
import com.example.artspaceapp.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

private const val ARTWORK_COUNT = 3

class ArtSpaceViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ArtSpaceUiState())
    val uiState: StateFlow<ArtSpaceUiState> = _uiState.asStateFlow()

    fun onPrevious() {
        val current = _uiState.value.current
        showArtwork(if (current == 1) ARTWORK_COUNT else current - 1)
    }

    fun onNext() {
        val current = _uiState.value.current
        showArtwork(if (current == ARTWORK_COUNT) 1 else current + 1)
    }

    private fun showArtwork(number: Int) {
        _uiState.update { currentState ->
            currentState.copy(current = number, artwork = artworkFor(number))
        }
    }

    // Конструкция when выбирает данные картины
    private fun artworkFor(number: Int): Artwork = when (number) {
        1 -> Artwork(R.drawable.art1, R.string.art1_title, R.string.art1_artist, R.string.art1_year)
        2 -> Artwork(R.drawable.art2, R.string.art2_title, R.string.art2_artist, R.string.art2_year)
        else -> Artwork(R.drawable.art3, R.string.art3_title, R.string.art3_artist, R.string.art3_year)
    }
}