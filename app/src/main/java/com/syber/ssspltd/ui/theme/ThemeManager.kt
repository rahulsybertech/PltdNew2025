package com.syber.ssspltd.ui.theme

import androidx.compose.ui.graphics.Color
import com.syber.ssspltd.R

object ThemeManager {
    fun getThemeColors(theme: AppThemeType): ThemeColors {
        return when (theme) {
            AppThemeType.DIWALI -> ThemeColors(
                primary = Color(0xFF008080),
                background = Color.Red,
                festiveImageRes = R.drawable.ssslogopng,
            /*    accent = Color(0xFFFFC107),
                buttonColor = Color(0xFFFF5722),
                titleTextColor = Color(0xFF4E342E)*/
            )
            AppThemeType.HOLI -> ThemeColors(
                primary = Color(0xFF008080),
                background = Color.Green,
                festiveImageRes = R.drawable.ic_launcher_background,
              /*  accent = Color(0xFFE040FB),
                buttonColor = Color(0xFFD81B60),
                titleTextColor = Color(0xFF4A148C)*/
            )
            else -> ThemeColors(
                primary =Color.White,
                background = Color.White,
                festiveImageRes = R.drawable.ic_launcher_foreground,
            /*    accent = Color(0xFF03A9F4),
                buttonColor = Color(0xFF0288D1),
                titleTextColor = Color.Black*/
            )
        }
    }
}

