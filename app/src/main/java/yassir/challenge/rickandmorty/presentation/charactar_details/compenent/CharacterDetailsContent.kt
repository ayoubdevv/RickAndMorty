package yassir.challenge.rickandmorty.presentation.charactar_details.compenent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import yassir.challenge.rickandmorty.R
import yassir.challenge.rickandmorty.presentation.charactar_details.state.CharacterDetailItem
import yassir.challenge.rickandmorty.presentation.commo.StatusChip
import yassir.challenge.rickandmorty.presentation.theme.AppTheme

@Composable
fun CharacterDetailsContent(
    item: CharacterDetailItem,
    onBackClicked: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column(modifier = modifier) {

        DetailsHeader(
            item = item,
            modifier = Modifier.weight(1.5f),
            onBackClicked = onBackClicked
        )

        DetailsContent(
            item = item,
            modifier = Modifier.weight(1f)
        )

    }


}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun DetailsHeader(
    item: CharacterDetailItem,
    onBackClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
    ) {

        GlideImage(
            modifier = Modifier.matchParentSize(),
            model = item.imageUrl,
            loading = placeholder(ColorPainter(Color.Gray)),
            failure = placeholder(ColorPainter(Color.Red)),
            contentDescription = item.name
        )

        IconButton(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.TopStart),
            colors = IconButtonDefaults.filledTonalIconButtonColors(),
            onClick = onBackClicked,
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = stringResource(R.string.back)
            )
        }

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
            onBackClicked = {},
            item = CharacterDetailItem(
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
    }

}