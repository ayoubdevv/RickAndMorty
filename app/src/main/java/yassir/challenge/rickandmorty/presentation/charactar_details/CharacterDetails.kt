package yassir.challenge.rickandmorty.presentation.charactar_details

import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import yassir.challenge.rickandmorty.R
import yassir.challenge.rickandmorty.presentation.charactar_details.compenent.CharacterDetailsContent
import yassir.challenge.rickandmorty.presentation.character_list.openNetworkSettings

@Composable
fun CharacterDetails(
    viewModel: CharacterDetailsViewModel = hiltViewModel(),
    onBackClicked: () -> Unit
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    val context = LocalContext.current


    LaunchedEffect(Unit) { viewModel::loadCharacterDetails }


    Scaffold {
        Box(Modifier.padding(it)) {
            CharacterDetailsContent(
                state = state,
                onRetry = viewModel::onRetry,
                onNetworkSettings = {
                    context.openNetworkSettings()
                },
            )
            BackButton(onBackClicked = onBackClicked)
        }
    }


}

@Composable
private fun BoxScope.BackButton(onBackClicked: () -> Unit) {
    IconButton(
        modifier = Modifier
            .padding(16.dp)
            .align(Alignment.TopStart),
        colors = IconButtonDefaults.filledTonalIconButtonColors(),
        onClick = onBackClicked,
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Default.ArrowBack,
            contentDescription = stringResource(R.string.back)
        )
    }
}

