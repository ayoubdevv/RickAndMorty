package yassir.challenge.rickandmorty.presentation.charactar_details.compenent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import yassir.challenge.rickandmorty.presentation.commo.shimmerEffect
import yassir.challenge.rickandmorty.presentation.theme.AppTheme


@Composable
fun DetailsLoading(modifier: Modifier = Modifier) {



        Column(modifier = modifier) {

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1.5f)
                    .background(shimmerEffect(), RoundedCornerShape(16.dp))

            )


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                repeat(5) {
                    Spacer(
                        modifier = Modifier
                            .background(shimmerEffect(), CircleShape)
                            .fillMaxWidth()
                            .height(24.dp)
                    )
                }
            }
        }
}


@Preview
@Composable
private fun DetailsLoadingPreview() {
    AppTheme {
        DetailsLoading()
    }

}