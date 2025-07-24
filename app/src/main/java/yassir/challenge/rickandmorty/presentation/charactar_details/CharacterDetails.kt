package yassir.challenge.rickandmorty.presentation.charactar_details

import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import yassir.challenge.rickandmorty.presentation.charactar_details.compenent.CharacterDetailsContent

@Composable
fun CharacterDetails(
    viewModel: CharacterDetailsViewModel = hiltViewModel(),
    onBackClicked : () -> Unit
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    state.detailItem?.let {
        CharacterDetailsContent(
            modifier = Modifier.statusBarsPadding(),
            item = it,
            onBackClicked = onBackClicked
        )
    }

}

