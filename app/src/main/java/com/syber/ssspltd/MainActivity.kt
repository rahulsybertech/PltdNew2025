package com.syber.ssspltd

import android.app.ComponentCaller
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.syber.ssspltd.ui.theme.AppThemeType
import com.syber.ssspltd.ui.theme.ThemeColors
import com.syber.ssspltd.ui.theme.ThemeManager
import com.syber.ssspltd.ui.view.home.HomeScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val startScreen = intent?.getStringExtra("screen")

        setContent {
            AppEntry(startScreen)
        }
    }

    override fun onNewIntent(intent: Intent, caller: ComponentCaller) {
        super.onNewIntent(intent, caller)
        setIntent(intent)
    }
}

@Composable
fun AppEntry(startScreen: String?) {

    val navController = rememberNavController()

    LaunchedEffect(startScreen) {
        when (startScreen) {
            "order" -> {
                navController.navigate("order") {
                    popUpTo("home") { inclusive = true }
                }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        val themeColors = ThemeManager.getThemeColors(AppThemeType.HOLI)
        composable("home") {
            HomeScreen(
                navController = navController,
                viewModel1 = hiltViewModel(),
                themeColors = themeColors
            )
        }

        composable("order") {
           // OrderScreen()
        }
    }
}

