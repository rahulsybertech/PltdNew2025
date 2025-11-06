package com.syber.ssspltd.ui.theme

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier



@Composable
fun MyApp(themeType: AppThemeType, content: @Composable () -> Unit) {
    val themeColors = ThemeManager.getThemeColors(themeType)

    val colorScheme = lightColorScheme(
        primary = themeColors.primary,
        background = themeColors.background
    )

    MaterialTheme(
        colorScheme = colorScheme
    ) {
        Surface(modifier = Modifier.fillMaxSize()) {
            content()
        }
    }
}

