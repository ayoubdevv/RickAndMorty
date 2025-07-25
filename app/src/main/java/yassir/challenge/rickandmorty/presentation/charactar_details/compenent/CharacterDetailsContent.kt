package yassir.challenge.rickandmorty.presentation.charactar_details.compenent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import yassir.challenge.rickandmorty.R
import yassir.challenge.rickandmorty.presentation.charactar_details.state.CharacterDetailItem
import yassir.challenge.rickandmorty.presentation.charactar_details.state.CharacterDetailsState
import yassir.challenge.rickandmorty.presentation.character_list.compenent.ErrorScreen
import yassir.challenge.rickandmorty.presentation.commo.StatusChip
import yassir.challenge.rickandmorty.presentation.commo.shimmerEffect
import yassir.challenge.rickandmorty.presentation.theme.AppTheme

@Composable
fun CharacterDetailsContent(
    state : CharacterDetailsState,
    onRetry: () -> Unit,
    onNetworkSettings: () -> Unit,
    modifier: Modifier = Modifier
) {

    Surface {
        when {
            state.isLoading -> DetailsLoading(modifier = modifier)

            state.hasError -> {
                ErrorScreen(
                    modifier = modifier.fillMaxSize(),
                    errorMessage = stringResource(state.errorMessage ?: R.string.error_unknown),
                    isNetworkError = state.isNetworkError,
                    onRetry = onRetry,
                    onNetworkSettings = onNetworkSettings
                )
            }

            state.detailItem != null -> {
                Column(modifier = modifier) {
                    DetailsHeader(
                        item = state.detailItem,
                        modifier = Modifier.weight(1.5f).background(shimmerEffect())
                    )
                    DetailsContent(
                        item = state.detailItem,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

    }




}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun DetailsHeader(
    item: CharacterDetailItem,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp))
    ) {

        GlideImage(
            modifier = Modifier.fillMaxSize(),
            model = item.imageUrl,
            loading = placeholder(ColorPainter(Color.Gray)),
            failure = placeholder(ColorPainter(Color.Red)),
            contentDescription = item.name,
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.matchParentSize().background(Color.Black.copy(.3f)))

        CharacterName(
            name = item.name,
            status = item.status,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun DetailsContent(item: CharacterDetailItem, modifier: Modifier = Modifier) {

    Surface(modifier = modifier) {
        Column {
            CharacterInfoRow("Species", item.species)
            CharacterInfoRow("Gender", item.gender)
            CharacterInfoRow("Location", item.location)
            CharacterInfoRow("Origin", item.origin)
            CharacterInfoRow("Episode Count", item.episodeCount.toString())
        }
    }

}


@Composable
fun CharacterInfoRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text(
                text = "$label:",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Medium
            )
        }
        HorizontalDivider()
    }
}

@Composable
fun CharacterName(name: String, status: String, modifier: Modifier = Modifier) {

    Row(
        modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.onBackground.copy(.3f))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier

        )

        Spacer(modifier = Modifier.weight(1f))

        StatusChip(status = status)
    }

}


@Preview(device = "spec:width=411dp,height=891dp", showSystemUi = true, showBackground = false)
@Composable
private fun CharacterDetailsContentPreview() {
    AppTheme {
        CharacterDetailsContent(
            modifier = Modifier.statusBarsPadding(),
            onRetry = {},
            onNetworkSettings = {},
            state = CharacterDetailsState(
                detailItem = CharacterDetailItem(
                    id = 1,
                    name = "Rick Sanchez",
                    status = "Alive",
                    species = "Human",
                    gender = "Male",
                    imageUrl = "",
                    origin = "Earth (C-137)",
                    episodeCount = 1,
                    location = "USA"
                )
            )
        )
    }

}