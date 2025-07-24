package yassir.challenge.rickandmorty.presentation.character_list.compenent

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import yassir.challenge.rickandmorty.presentation.character_list.state.CharacterListItem
import yassir.challenge.rickandmorty.presentation.commo.GenderText
import yassir.challenge.rickandmorty.presentation.commo.StatusChip
import yassir.challenge.rickandmorty.ui.theme.AppTheme


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CharacterItem(
    item: CharacterListItem,
    onItemClicked: () -> Unit,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier,
        onClick = onItemClicked
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        ) {

            GlideImage(
                modifier = Modifier.fillMaxSize(),
                model = item.imageUrl,
                loading = placeholder(ColorPainter(Color.Gray)),
                failure = placeholder(ColorPainter(Color.Red)),
                contentDescription = item.name
            )

            Row(modifier = Modifier.padding(16.dp)) {

                StatusChip(status = item.status)

                Spacer(modifier = Modifier.weight(1f))

                GenderText(gender = item.status)
            }

        }

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = item.name,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = item.name,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}


@Preview
@Composable
private fun CharacterItemPreview() {
    AppTheme {
        CharacterItem(
            item = CharacterListItem(
                id = 1,
                name = "Rick Sanchez",
                status = "Alive",
                species = "Human",
                gender = "Male",
                imageUrl = ""
            ),
            onItemClicked = {})
    }

}