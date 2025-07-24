package yassir.challenge.rickandmorty.presentation.charactar_details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import yassir.challenge.rickandmorty.presentation.charactar_details.compenent.CharacterDetailsContent
import yassir.challenge.rickandmorty.presentation.theme.AppTheme

@Composable
fun CharacterDetails(
    viewModel: CharacterDetailsViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()



    state.detailItem?.let {
        CharacterDetailsContent(
            item = it,
            onBackClicked = {}
        )
    }

}

