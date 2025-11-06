package com.syber.ssspltd.ui.view.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.compose.rememberNavController
import com.syber.ssspltd.R
import com.syber.ssspltd.navigation.AppNavGraph
import com.syber.ssspltd.out.AuthViewModel

class HomeActivity : ComponentActivity() {
    private val viewModell by viewModels<AuthViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // Set up the composable content
            val navController = rememberNavController()
            AppNavGraph(navController = navController)
            /*  LoginScreen(navController = navController,viewModell)*/
        }
    }
}