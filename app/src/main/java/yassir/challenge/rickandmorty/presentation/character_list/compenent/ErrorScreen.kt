package yassir.challenge.rickandmorty.presentation.character_list.compenent

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import yassir.challenge.rickandmorty.R
import yassir.challenge.rickandmorty.presentation.theme.AppTheme

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    isNetworkError: Boolean = true,
    onRetry: () -> Unit = {},
    onNetworkSettings :() -> Unit = {}
) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Image(
            painter = painterResource(if (isNetworkError) R.drawable.no_internet else R.drawable.error),
            contentDescription = stringResource(if (isNetworkError) R.string.no_internet_title else R.string.error_title)
        )

        Text(
            text = stringResource(if (isNetworkError) R.string.no_internet_title else R.string.error_title),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Text(
            text = stringResource(if (isNetworkError) R.string.no_internet_subtitle else   R.string.error_subtitle),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Button(onClick =  if (isNetworkError) onNetworkSettings else onRetry) {
            Text(stringResource(if (isNetworkError) R.string.enable_wifi_button else R.string.retry_button))
        }
    }
}


@Preview
@Composable
private fun ErrorScreenPreview() {
    AppTheme {
        ErrorScreen(isNetworkError = false)
    }

}