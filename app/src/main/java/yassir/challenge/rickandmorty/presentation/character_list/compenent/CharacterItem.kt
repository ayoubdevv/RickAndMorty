package yassir.challenge.rickandmorty.presentation.character_list.compenent

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import yassir.challenge.rickandmorty.data.repository.FakeCharacterRepository
import yassir.challenge.rickandmorty.presentation.character_list.state.CharacterListItem
import yassir.challenge.rickandmorty.ui.theme.AppTheme


@Composable
fun CharacterItem(
    item : CharacterListItem,
    onItemClicked : () -> Unit,
    modifier: Modifier = Modifier
) {

}


@Preview
@Composable
private fun CharacterItemPreview() {
    AppTheme {
        CharacterItem(
            item = CharacterListItem(
                id = 1,
                name = "Rick Sanchez",
                status = "Alive",
                species = "Human",
                image = ""
            ),
            onItemClicked = {})
    }

}