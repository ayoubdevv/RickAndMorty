package yassir.challenge.rickandmorty.presentation.character_list.compenent

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import yassir.challenge.rickandmorty.data.remote.toDomain
import yassir.challenge.rickandmorty.data.repository.FakeCharacterRepository
import yassir.challenge.rickandmorty.presentation.character_list.state.CharacterListAction
import yassir.challenge.rickandmorty.presentation.character_list.state.CharacterListState
import yassir.challenge.rickandmorty.presentation.mapper.toListItem
import yassir.challenge.rickandmorty.presentation.theme.AppTheme


@Composable
fun CharacterListContent(
    state: CharacterListState,
    onAction: (CharacterListAction) -> Unit,
    modifier: Modifier = Modifier
) {


    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ) {

        items(items = state.characterList, key = { it.id }) { item ->
            CharacterItem(
                item = item,
                onItemClicked = { onAction(CharacterListAction.OnItemClicked(item.id)) }
            )
        }
    }

}


@Preview(device = "spec:width=411dp,height=891dp", showBackground = false, showSystemUi = true)
@Composable
private fun CharacterListContentPreview() {
    AppTheme {
        CharacterListContent(
            modifier = Modifier.statusBarsPadding(),
            state = CharacterListState(
                characterList = FakeCharacterRepository.getCharacters().map { it.toDomain().toListItem() }),
            onAction = {}
        )
    }

}