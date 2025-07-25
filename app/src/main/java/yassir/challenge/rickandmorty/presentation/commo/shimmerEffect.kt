package yassir.challenge.rickandmorty.presentation.commo

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import yassir.challenge.rickandmorty.AppThemeMode
import yassir.challenge.rickandmorty.LocalAppTheme


@Composable
fun shimmerEffect(): Brush {
    val transition = rememberInfiniteTransition()

    val themeState = LocalAppTheme.current
    val isDarkTheme = themeState.value == AppThemeMode.DARK

    val shimmerColors =  if (isDarkTheme) {
        listOf(
            Color.DarkGray.copy(alpha = 0.6f),
            Color.Gray.copy(alpha = 0.2f),
            Color.DarkGray.copy(alpha = 0.6f)
        )
    } else {
        listOf(
            Color.LightGray.copy(alpha = 0.6f),
            Color.LightGray.copy(alpha = 0.2f),
            Color.LightGray.copy(alpha = 0.6f)
        )
    }

    val translateAnimation by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    return Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(x = translateAnimation, y = translateAnimation),
        end = Offset(x = translateAnimation + 100f, y = translateAnimation + 100f),
    )
}