package yassir.challenge.rickandmorty.presentation.character_list.compenent

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
fun EmptyScreen(modifier: Modifier = Modifier) {


    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.ic_no_result),
            contentDescription = stringResource(R.string.no_results_title)
        )

        Text(
            text = stringResource(R.string.no_results_title),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Text(
            text = stringResource(R.string.no_results_subtitle),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

       /* Button(onClick = {}) {
            Text(stringResource(R.string.search_again_button))
        }*/
    }

}


@Preview
@Composable
private fun EmptyScreenPreview() {
    AppTheme {
        Surface {
            EmptyScreen()
        }
    }

}