package yassir.challenge.rickandmorty.presentation.character_list.compenent

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells.*
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import yassir.challenge.rickandmorty.presentation.character_list.state.CharacterListAction
import yassir.challenge.rickandmorty.presentation.character_list.state.CharacterListAction.*
import yassir.challenge.rickandmorty.presentation.character_list.state.CharacterPagingState
import yassir.challenge.rickandmorty.presentation.theme.AppTheme


@Composable
fun CharacterListContent(
    state: CharacterPagingState,
    onAction: (CharacterListAction) -> Unit,
    modifier: Modifier = Modifier
) {

    when (state) {

        is CharacterPagingState.Error -> {
            ErrorScreen(
                modifier = modifier.fillMaxSize(),
                isNetworkError = state.isNetworkError,
                errorMessage = state.message,
                onRetry = { onAction(OnErrorRetry) },
                onNetworkSettings = { onAction(OnErrorNetworkSettings) })
        }

        is CharacterPagingState.Loading -> LoadingScreen(modifier = modifier)

        is CharacterPagingState.Empty -> EmptyScreen(modifier = modifier.fillMaxSize())

        is CharacterPagingState.Success -> CharacterList(modifier, state, onAction)

        else -> Unit
    }
}

@Composable
private fun CharacterList(
    modifier: Modifier,
    state: CharacterPagingState.Success,
    onAction: (CharacterListAction) -> Unit
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    val column = if (isLandscape) 4 else 2

    LazyVerticalGrid(
        modifier = modifier,
        columns = Fixed(column),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp)
    ) {

        items(state.characters.itemCount) { index ->

            val item = state.characters[index]
            if (item != null) {
                CharacterItem(
                    item = item,
                    onItemClicked = { onAction(OnItemClicked(item.id)) }
                )
            }
        }
    }
}


@Preview(device = "spec:width=411dp,height=891dp", showBackground = false, showSystemUi = true)
@Composable
private fun CharacterListContentPreview() {
    AppTheme {
        CharacterListContent(
            modifier = Modifier.statusBarsPadding(),
            state = CharacterPagingState.Error(message = "Error", isNetworkError = false),
            onAction = {}
        )
    }

}