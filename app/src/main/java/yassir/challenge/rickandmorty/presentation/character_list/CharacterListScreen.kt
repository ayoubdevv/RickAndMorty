package yassir.challenge.rickandmorty.presentation.character_list

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import yassir.challenge.rickandmorty.presentation.character_list.compenent.CharacterListContent
import yassir.challenge.rickandmorty.presentation.character_list.compenent.CharacterTopBar
import yassir.challenge.rickandmorty.presentation.character_list.state.CharacterListAction


@Composable
fun CharacterListScreen(
    viewModel: CharacterViewModel = hiltViewModel(),
    navigateToDetail: (Int) -> Unit
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel.events) {
        viewModel.events.collect { event ->
            when (event) {
                is CharacterViewModel.UiEvent.NavigateToDetail -> {
                    navigateToDetail(event.characterId)
                }
            }
        }
    }


    Scaffold(
        topBar = {
            CharacterTopBar(
                isSearching = state.isSearching,
                searchQuery = state.searchQuery,
                onSearchToggle = { viewModel.onAction(CharacterListAction.OnSearchToggle) },
                onSearchQueryChanged = { viewModel.onAction(CharacterListAction.SnSearchQueryChanged(it)) },
            )
        }
    )
    { contentPadding ->

        CharacterListContent(
            modifier = Modifier.padding(contentPadding),
            state = state,
            onAction = viewModel::onAction
        )
    }

}