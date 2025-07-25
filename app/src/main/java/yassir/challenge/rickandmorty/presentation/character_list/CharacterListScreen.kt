package yassir.challenge.rickandmorty.presentation.character_list

import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import retrofit2.HttpException
import yassir.challenge.rickandmorty.R
import yassir.challenge.rickandmorty.presentation.character_list.compenent.CharacterListContent
import yassir.challenge.rickandmorty.presentation.character_list.compenent.CharacterTopBar
import yassir.challenge.rickandmorty.presentation.character_list.state.CharacterListAction
import yassir.challenge.rickandmorty.presentation.character_list.state.CharacterListItem
import yassir.challenge.rickandmorty.presentation.character_list.state.CharacterPagingState
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


@Composable
fun CharacterListScreen(
    viewModel: CharacterViewModel = hiltViewModel(),
    navigateToDetail: (Int) -> Unit
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    val pagingItems = viewModel.characterPaging.collectAsLazyPagingItems()

    val context = LocalContext.current

    LaunchedEffect(viewModel.events) {
        viewModel.events.collect { event ->
            when (event) {
                is CharacterViewModel.UiEvent.NavigateToDetail -> {
                    navigateToDetail(event.characterId)
                }
                CharacterViewModel.UiEvent.OnErrorNetworkSettings ->  context.openNetworkSettings()
                CharacterViewModel.UiEvent.OnErrorRetry -> pagingItems.retry()
            }
        }
    }


    val pagingItemsState = characterPagingStateProvider(pagingItems)

    Scaffold(
        topBar = {
            CharacterTopBar(
                isSearching = state.isSearching,
                searchQuery = state.searchQuery,
                onSearchToggle = { viewModel.onAction(CharacterListAction.OnSearchToggle) },
                onSearchQueryChanged = { viewModel.onAction(CharacterListAction.OnSearchQueryChanged(it)) },
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
 fun Context.openNetworkSettings() {
    val intent = Intent(Settings.ACTION_WIRELESS_SETTINGS).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
    }
    startActivity(intent)
}

@Composable
private fun characterPagingStateProvider(pagingItems: LazyPagingItems<CharacterListItem>): CharacterPagingState {
    return when (val loadState = pagingItems.loadState.refresh) {
        is LoadState.Loading -> CharacterPagingState.Loading
        is LoadState.Error -> {
            val error = loadState.error
            val isNetworkError = error is IOException
            if (error is HttpException && error.code() == 404) {
                CharacterPagingState.Empty
            } else {
                CharacterPagingState.Error(error.toUserReadableMessage(),isNetworkError)
            }
        }
        is LoadState.NotLoading -> CharacterPagingState.Success(pagingItems)
    }

}

@Composable
fun Throwable.toUserReadableMessage(): String {
    val errorMessage = when (this) {
        is UnknownHostException -> stringResource(R.string.no_internet_title)
        is SocketTimeoutException -> stringResource(R.string.error_timeout)
        else -> stringResource(R.string.error_subtitle)
    }
    return errorMessage
}