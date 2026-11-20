package com.example.rickandmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.rickandmorty.ui.character.CharacterScreen
import com.example.rickandmorty.ui.characters.CharactersScreen
import com.example.rickandmorty.ui.theme.RickAndMortyTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RickAndMortyTheme {
                val navController = rememberNavController()
                val backStackEntry by navController.currentBackStackEntryAsState()
                // открыт ли сейчас экран конкретного персонажа
                val isDetails = backStackEntry?.destination?.route?.startsWith("character/") == true

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text(if (isDetails) "Персонаж" else "Персонажи") },
                            navigationIcon = {
                                if (isDetails) {
                                    IconButton(onClick = { navController.navigateUp() }) {
                                        Text("←", fontSize = 24.sp)
                                    }
                                }
                            }
                        )
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "characters",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("characters") {
                            CharactersScreen(
                                onCharacterClick = { id -> navController.navigate("character/$id") }
                            )
                        }
                        composable(
                            route = "character/{characterId}",
                            arguments = listOf(navArgument("characterId") { type = NavType.IntType })
                        ) {
                            CharacterScreen()
                        }
                    }
                }
            }
        }
    }
}