package yassir.challenge.rickandmorty.presentation.character_list.compenent

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import yassir.challenge.rickandmorty.presentation.character_list.state.CharacterListItem
import yassir.challenge.rickandmorty.presentation.commo.GenderText
import yassir.challenge.rickandmorty.presentation.commo.StatusChip
import yassir.challenge.rickandmorty.presentation.theme.AppTheme


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CharacterItem(
    item: CharacterListItem,
    onItemClicked: () -> Unit,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        onClick = onItemClicked
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(1f)
        ) {

            GlideImage(
                modifier = Modifier.fillMaxSize(),
                model = item.imageUrl,
                loading = placeholder(ColorPainter(Color.Gray)),
                failure = placeholder(ColorPainter(Color.Red)),
                contentDescription = item.name,
                contentScale = ContentScale.Crop
            )

            DimOverlay()

            Row(modifier = Modifier.padding(horizontal = 8.dp)) {

                StatusChip(status = item.status)

                Spacer(modifier = Modifier.weight(1f))

                GenderText(gender = item.gender)
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = item.name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                    color = Color.White
                )

                Text(
                    text = item.species,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(.8f)
                )
            }
        }

    }
}

@Composable
private fun BoxScope.DimOverlay() {
    val isDarkTheme = isSystemInDarkTheme()

    val overlayColor = if (isDarkTheme) {
        MaterialTheme.colorScheme.surface.copy(alpha = 0.85f)
    } else {
        Color.Black.copy(alpha = 0.85f)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .align(Alignment.BottomCenter)
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        overlayColor
                    )
                )
            )
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .align(Alignment.TopCenter)
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        overlayColor,
                        Color.Transparent
                    )
                )
            )
    )
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