package yassir.challenge.rickandmorty.presentation.character_list.compenent

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import yassir.challenge.rickandmorty.R
import yassir.challenge.rickandmorty.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterTopBar(
    isSearching: Boolean,
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    onSearchToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (isSearching) {
        TextField(
            value = searchQuery,
            onValueChange = onSearchQueryChanged,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            placeholder = { Text(stringResource(R.string.topbar_search_characters_hint)) },
            singleLine = true,
            trailingIcon = {
                IconButton(onClick = onSearchToggle) {
                    Icon(Icons.Default.Close, contentDescription = stringResource(R.string.topbar_close_search_content_description))
                }
            }
        )
    } else {
        TopAppBar(
            modifier = modifier,
            title = { Text(stringResource(R.string.topbar_title_characters)) },
            actions = {
                IconButton(onClick = onSearchToggle) {
                    Icon(Icons.Default.Search, contentDescription = stringResource(R.string.topbar_search_btn_content_description))
                }
            }
        )
    }
}


@Preview
@Composable
private fun CharacterTopBarPreview() {
    AppTheme {

        var isSearching by remember{ mutableStateOf(false) }
        var searchQuery by remember{ mutableStateOf("") }

        CharacterTopBar(
            isSearching = isSearching,
            searchQuery = searchQuery,
            onSearchToggle = {isSearching = !isSearching},
            onSearchQueryChanged = {
                searchQuery = it
            }
        )
    }

}