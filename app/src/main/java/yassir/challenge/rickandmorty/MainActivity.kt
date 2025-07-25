package yassir.challenge.rickandmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dagger.hilt.android.AndroidEntryPoint
import yassir.challenge.rickandmorty.presentation.MainScreen
import yassir.challenge.rickandmorty.presentation.theme.AppTheme


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val themeState = remember { mutableStateOf(AppThemeMode.LIGHT) }

            CompositionLocalProvider(LocalAppTheme provides themeState) {
                val isDark = themeState.value == AppThemeMode.DARK
                AppTheme(isDark) {
                    MainScreen()
                }
            }

        }
    }
}

enum class AppThemeMode { LIGHT, DARK }

val LocalAppTheme = compositionLocalOf { mutableStateOf(AppThemeMode.LIGHT) }

