package yassir.challenge.rickandmorty.presentation.character_list.compenent

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import yassir.challenge.rickandmorty.AppThemeMode
import yassir.challenge.rickandmorty.LocalAppTheme
import yassir.challenge.rickandmorty.R
import yassir.challenge.rickandmorty.presentation.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterTopBar(
    isSearching: Boolean,
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    onSearchToggle: () -> Unit,
    modifier: Modifier = Modifier
) {

    val themeState = LocalAppTheme.current
    val isDark = themeState.value == AppThemeMode.DARK

    AnimatedContent(targetState = isSearching, transitionSpec = {
        fadeIn(tween(300)) togetherWith fadeOut(
            tween(300)
        )
    }) { isSearching ->
        if (isSearching) {
            TextField(
                value = searchQuery,
                onValueChange = onSearchQueryChanged,
                modifier = modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(16.dp),
                placeholder = { Text(stringResource(R.string.topbar_search_characters_hint)) },
                singleLine = true,
                trailingIcon = {
                    IconButton(onClick = onSearchToggle) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = stringResource(R.string.topbar_close_search_content_description)
                        )
                    }
                }
            )
        } else {
            TopAppBar(
                modifier = modifier,
                title = { Text(stringResource(R.string.topbar_title_characters)) },
                actions = {
                    IconButton(onClick = onSearchToggle) {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = stringResource(R.string.topbar_search_btn_content_description)
                        )
                    }

                    IconButton(onClick = {
                        themeState.value = if (isDark) AppThemeMode.LIGHT else AppThemeMode.DARK
                    }) {
                        Icon(
                            painter = painterResource(if (isDark) R.drawable.ic_light else R.drawable.ic_dark),
                            contentDescription = stringResource(R.string.topbar_search_btn_content_description)
                        )
                    }
                }
            )
        }
    }

}


@Preview
@Composable
private fun CharacterTopBarPreview() {
    AppTheme {

        var isSearching by remember { mutableStateOf(false) }
        var searchQuery by remember { mutableStateOf("") }

        CharacterTopBar(
            isSearching = isSearching,
            searchQuery = searchQuery,
            onSearchToggle = { isSearching = !isSearching },
            onSearchQueryChanged = {
                searchQuery = it
            }
        )
    }

}