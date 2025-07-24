package yassir.challenge.rickandmorty.presentation.character_list.compenent

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridCells.*
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import yassir.challenge.rickandmorty.data.remote.toDomain
import yassir.challenge.rickandmorty.data.repository.FakeCharacterRepository
import yassir.challenge.rickandmorty.presentation.character_list.state.CharacterListAction
import yassir.challenge.rickandmorty.presentation.character_list.state.CharacterListAction.*
import yassir.challenge.rickandmorty.presentation.character_list.state.CharacterListState
import yassir.challenge.rickandmorty.presentation.character_list.state.CharacterPagingState
import yassir.challenge.rickandmorty.presentation.mapper.toListItem
import yassir.challenge.rickandmorty.presentation.theme.AppTheme


@Composable
fun CharacterListContent(
    state: CharacterPagingState,
    onAction: (CharacterListAction) -> Unit,
    modifier: Modifier = Modifier
) {

    when (state) {

        is CharacterPagingState.Error -> ErrorScreen(modifier = modifier, message = state.message)

        is CharacterPagingState.Loading -> LoadingScreen(modifier = modifier)

        is CharacterPagingState.Success -> {
            LazyVerticalGrid(
                modifier = modifier,
                columns = Fixed(2),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(16.dp)
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

        else -> Unit
    }
}

@Composable
fun EmptyScreen(message: String, modifier: Modifier = Modifier) {
    Text("EMPTY : $message", modifier)
}

@Composable
fun ErrorScreen(message: String, modifier: Modifier = Modifier) {
    Text(text = "ERROR : $message", modifier)
}

@Composable
fun LoadingScreen(modifier : Modifier = Modifier) {
    CircularProgressIndicator(modifier = modifier)
}


@Preview(device = "spec:width=411dp,height=891dp", showBackground = false, showSystemUi = true)
@Composable
private fun CharacterListContentPreview() {
    AppTheme {
        CharacterListContent(
            modifier = Modifier.statusBarsPadding(),
            state = CharacterPagingState.Error(message = "Error"),
            onAction = {}
        )
    }

}