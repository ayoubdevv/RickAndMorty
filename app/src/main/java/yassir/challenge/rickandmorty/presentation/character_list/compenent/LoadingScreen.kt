package yassir.challenge.rickandmorty.presentation.character_list.compenent

import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells.Fixed
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import yassir.challenge.rickandmorty.presentation.commo.shimmerEffect
import yassir.challenge.rickandmorty.presentation.theme.AppTheme

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {

    LazyVerticalGrid(
        modifier = modifier,
        columns = Fixed(2),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp)
    ) {

        items(20) {
            LoadingCharacterItem()
        }
    }

}

@Composable
fun LoadingCharacterItem() {

    val brush = shimmerEffect()




    Card {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .background(brush)

        )


        Column(
            modifier = Modifier.padding(16.dp),verticalArrangement = Arrangement.spacedBy(8.dp)) {

            Spacer(modifier = Modifier.fillMaxWidth(.6f).height(16.dp).background(brush, CircleShape))

            Spacer(modifier = Modifier.fillMaxWidth(.4f).height(16.dp).background(brush,CircleShape))
        }

    }
}

@Preview
@Composable
private fun LoadingPreview() {
    AppTheme {
        LoadingScreen()
    }
}