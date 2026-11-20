package com.example.artspaceapp.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.artspaceapp.R

data class Artwork(
    @DrawableRes val image: Int,
    @StringRes val title: Int,
    @StringRes val artist: Int,
    @StringRes val year: Int
)

data class ArtSpaceUiState(
    val current: Int = 1,
    val artwork: Artwork = Artwork(
        R.drawable.art1,
        R.string.art1_title,
        R.string.art1_artist,
        R.string.art1_year
    )
)