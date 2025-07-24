package yassir.challenge.rickandmorty.presentation.character_list

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import yassir.challenge.rickandmorty.R
import yassir.challenge.rickandmorty.presentation.character_list.compenent.CharacterListContent
import yassir.challenge.rickandmorty.presentation.character_list.compenent.CharacterTopBar
import yassir.challenge.rickandmorty.presentation.character_list.state.CharacterListAction
import yassir.challenge.rickandmorty.presentation.character_list.state.CharacterPagingState
import java.net.SocketTimeoutException
import java.net.UnknownHostException


@Composable
fun CharacterListScreen(
    viewModel: CharacterViewModel = hiltViewModel(),
    navigateToDetail: (Int) -> Unit
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    val pagingItems = viewModel.characterPaging.collectAsLazyPagingItems()

    LaunchedEffect(viewModel.events) {
        viewModel.events.collect { event ->
            when (event) {
                is CharacterViewModel.UiEvent.NavigateToDetail -> {
                    navigateToDetail(event.characterId)
                }
            }
        }
    }



    val pagingItemsState = when (val loadState = pagingItems.loadState.refresh) {
        is LoadState.Loading -> CharacterPagingState.Loading
        is LoadState.Error -> {
            val error = loadState.error
            CharacterPagingState.Error( error.toUserReadableMessage())
        }
        else -> CharacterPagingState.Success(pagingItems)
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
            state = pagingItemsState,
            onAction = viewModel::onAction
        )
    }

}

@Composable
private fun Throwable.toUserReadableMessage(): String {
    val errorMessage = when (this) {
        is UnknownHostException -> stringResource(R.string.error_no_internet)
        is SocketTimeoutException -> stringResource(R.string.error_timeout)
        else -> stringResource(R.string.error_unknown)
    }
    return errorMessage
}