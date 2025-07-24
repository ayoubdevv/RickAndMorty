package yassir.challenge.rickandmorty.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import yassir.challenge.rickandmorty.presentation.charactar_details.CharacterDetails
import yassir.challenge.rickandmorty.presentation.character_list.CharacterListScreen


@Composable
fun MainScreen(modifier: Modifier = Modifier) {

    val navController = rememberNavController()

    NavHost(modifier = modifier, navController = navController, startDestination = Destination.CharacterList) {

        composable<Destination.CharacterList> {
            CharacterListScreen { id ->
                navController.navigate(Destination.CharacterDetails(id))
            }
        }

        composable<Destination.CharacterDetails> {
            CharacterDetails()
        }
    }
}

@Serializable
sealed class Destination {

    @Serializable
    data object CharacterList : Destination()

    @Serializable
    data class CharacterDetails(val characterId: Int) : Destination()
}