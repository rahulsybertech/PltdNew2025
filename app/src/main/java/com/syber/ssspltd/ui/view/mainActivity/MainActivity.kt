package com.syber.ssspltd.ui.view.mainActivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.navigation.compose.rememberNavController
import com.syber.ssspltd.navigation.AppNavGraph
import com.syber.ssspltd.ui.theme.AppThemeType
import com.syber.ssspltd.ui.theme.MyApp
import com.syber.ssspltd.ui.theme.ThemeDataStore
import com.syber.ssspltd.utils.MyConstant
import javax.inject.Inject

class MainActivity : ComponentActivity() {
    @Inject
    lateinit var themeStore: ThemeDataStore // Optional via Hilt
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            // Asynchronously load the theme
          /*  val theme = when (MyConstant.THEMECOLUR.lowercase()) {
                "holi" -> AppThemeType.HOLI
                "diwali" -> AppThemeType.DIWALI
                else -> AppThemeType.DEFAULT
            }*/
            val currentTheme by produceState<AppThemeType>(initialValue = AppThemeType.DIWALI) {
                value = ThemeDataStore.getTheme(this@MainActivity)
            }

            MyApp(currentTheme) {
                AppNavGraph(navController = navController,)
            }
         //   AppNavGraph(navController = navController)
            /*MaterialTheme {
                MainScreen(navController)
            }*/
        }
    }
}