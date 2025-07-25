package yassir.challenge.rickandmorty.presentation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedContentTransitionScope.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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

        composable<Destination.CharacterList>(
            enterTransition = { slideIntoContainer(SlideDirection.Start, tween(700)) },
            exitTransition = { slideOutOfContainer(SlideDirection.Start, tween(700)) },
            popEnterTransition = { slideIntoContainer(SlideDirection.End, tween(700)) },
            popExitTransition = { slideOutOfContainer(SlideDirection.End, tween(700)) }
        ) {
            CharacterListScreen { id ->
                navController.navigate(Destination.CharacterDetails(id))
            }
        }

        composable<Destination.CharacterDetails>(
            enterTransition = { slideIntoContainer(SlideDirection.Start, tween(700)) },
            exitTransition = { slideOutOfContainer(SlideDirection.Start, tween(700)) },
            popEnterTransition = { slideIntoContainer(SlideDirection.End, tween(700)) },
            popExitTransition = { slideOutOfContainer(SlideDirection.End, tween(700)) }
        ) {
            CharacterDetails {
                navController.navigateUp()
            }
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