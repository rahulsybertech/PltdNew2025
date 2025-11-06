package com.syber.ssspltd.ui.view.splash

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.navigation.compose.rememberNavController
import com.google.gson.JsonObject
import com.syber.ssspltd.navigation.AppNavGraph
import com.syber.ssspltd.out.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : ComponentActivity() {
    private val viewModell by viewModels<AuthViewModel>()

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // Set up the composable content
            val navController = rememberNavController()
            AppNavGraph(navController = navController)

        }
    }




}