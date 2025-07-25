package yassir.challenge.rickandmorty.presentation.character_list.compenent

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import yassir.challenge.rickandmorty.R
import yassir.challenge.rickandmorty.presentation.character_list.state.CharacterPagingState
import yassir.challenge.rickandmorty.presentation.character_list.toUserReadableMessage
import yassir.challenge.rickandmorty.presentation.theme.AppTheme


fun LazyGridScope.characterGridPagingFooter(
    appendState: LoadState,
    state: CharacterPagingState.Success
) {
    if (appendState is LoadState.Loading) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            FooterLoading()
        }
    }
    if (appendState is LoadState.Error) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            val errorMessage = appendState.error.toUserReadableMessage()
            FooterError(errorMessage = errorMessage, retry = { state.characters.retry() })
        }
    }
}

@Composable
private fun FooterError(
    errorMessage: String,
    retry: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(errorMessage)
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = retry) {
            Text(stringResource(R.string.retry_button))
        }
    }
}

@Composable
private fun FooterLoading() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}


@Preview
@Composable
private fun CharacterGridPagingFooterPreview() {
    AppTheme {
        Column {
            FooterLoading()
            FooterError(
                stringResource(R.string.no_internet_title),
                {})
        }
    }

}
