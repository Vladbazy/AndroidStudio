package com.example.artspaceapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.artspaceapp.ui.theme.ArtSpaceAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ArtSpaceScreen()
                }
            }
        }
    }
}

data class Artwork(
    @DrawableRes val image: Int,
    @StringRes val title: Int,
    @StringRes val artist: Int,
    @StringRes val year: Int
)

private const val ARTWORK_COUNT = 3

@Composable
fun ArtSpaceScreen() {
    // Номер текущей картины (сохраняется и при повороте экрана)
    var current by rememberSaveable { mutableStateOf(1) }

    // Конструкция when выбирает данные картины
    val artwork = when (current) {
        1 -> Artwork(R.drawable.art1, R.string.art1_title, R.string.art1_artist, R.string.art1_year)
        2 -> Artwork(R.drawable.art2, R.string.art2_title, R.string.art2_artist, R.string.art2_year)
        else -> Artwork(R.drawable.art3, R.string.art3_title, R.string.art3_artist, R.string.art3_year)
    }

    val onPrevious = { current = if (current == 1) ARTWORK_COUNT else current - 1 }
    val onNext = { current = if (current == ARTWORK_COUNT) 1 else current + 1 }

    // Широкий экран: ширина больше высоты
    val config = LocalConfiguration.current
    val isWide = config.screenWidthDp > config.screenHeightDp

    if (isWide) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding()
                .padding(24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            ArtworkWall(artwork, Modifier.weight(1f).fillMaxHeight())
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                ArtworkDescriptor(artwork)
                DisplayController(onPrevious, onNext)
            }
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            ArtworkWall(artwork, Modifier.fillMaxWidth().height(450.dp))
            ArtworkDescriptor(artwork)
            DisplayController(onPrevious, onNext)
        }
    }
}

@Composable
fun ArtworkWall(artwork: Artwork, modifier: Modifier = Modifier) {
    Surface(modifier = modifier, shadowElevation = 8.dp) {
        Image(
            painter = painterResource(artwork.image),
            contentDescription = stringResource(artwork.title),
            contentScale = ContentScale.Fit,
            modifier = Modifier.padding(24.dp)
        )
    }
}

@Composable
fun ArtworkDescriptor(artwork: Artwork, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(artwork.title),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Light
        )
        Text(
            text = buildAnnotatedString {
                withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(stringResource(artwork.artist))
                }
                append(" (${stringResource(artwork.year)})")
            }
        )
    }
}

@Composable
fun DisplayController(onPrevious: () -> Unit, onNext: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Button(onClick = onPrevious, modifier = Modifier.weight(1f)) {
            Text(stringResource(R.string.previous))
        }
        Button(onClick = onNext, modifier = Modifier.weight(1f)) {
            Text(stringResource(R.string.next))
        }
    }
}